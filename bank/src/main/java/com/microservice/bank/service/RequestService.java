package com.microservice.bank.service;
import com.microservice.bank.model.*;
import com.microservice.bank.repository.AccountRepository;
import com.microservice.bank.repository.RequestFromBankRepository;
import com.microservice.bank.repository.RequestRepository;
import com.microservice.bank.repository.ResponseFromBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RequestService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private RequestFromBankRepository requestFromBankRepository;

    @Autowired
    private ResponseFromBankRepository responseFromBankRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bank.pan}")
    private String myPan;

    @Value("${bank.bin}")
    private String bin;

    @Value("${pcc.url}")
    private String pccUrl;

    public Object generateResponse(Request request){

        String bankPaymentForm = "http://localhost:4205/paymentRequest";

        //System.out.println(request.toString());

        //System.out.println(request.getMerchantId());
        //Account account = merchantService.getAccountFromMerchant(request.getMerchantId(),request.getMerchantPassword());


//        if(account == null)
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Merchant");

//        if(request.getAmount() == null || request.getMerchantOrderId() == null  ||
//                request.getMerchantTimestamp() == null || request.getSuccessUrl() == null ||
//                request.getFailedUrl() == null || request.getErrorUrl() == null)
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Null");

        request.setPaymentId(generateRandomPaymentId());
        requestRepository.save(request);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(bankPaymentForm)
                .queryParam("paymentId", request.getPaymentId());

        String paymentUrl = builder.build().encode().toUriString();

        Response response = new Response();
        response.setPAYMENT_ID(request.getPaymentId());
        response.setPAYMENT_URL(paymentUrl);
        //OVDE REDIREKTUJ NA FORMU ZA PLACANJE

        return Collections.singletonMap("redirectUrl",paymentUrl);
    }

    public Object pay(Payment payment, String id){

        Account payerAccount = null;
        Date date = new Date();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        if(isRequestForMe(payment)) {

            System.out.println("FOR THIS BANK " + myPan);

            Account optionalAccount = accountService.getAccount(
                            payment.getPan(),
                            payment.getSecurityCode(),
                            payment.getCardHolderName(),
                            payment.getExpirationDate());

            payerAccount = optionalAccount;

            String redirectUrl;

            Request request = requestRepository.findRequestByPaymentId(id);

            if (payerAccount == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data!");

            if (payerAccount.getAmount() < request.getAmount()) {
                redirectUrl = request.getFailedUrl();
                ResponseEntity<Object> exchange = restTemplate.exchange(redirectUrl, HttpMethod.POST, requestEntity, Object.class);
                return exchange.getBody();
            }

            Transaction transactionDebit = new Transaction();

            transactionDebit.setAmount(request.getAmount());
            transactionDebit.setRequest(request);
            transactionDebit.setTransactionDate(new Date());
            transactionDebit.setAccount(payerAccount);

            payerAccount.setAmount(payerAccount.getAmount() - request.getAmount());

            transactionService.saveNew(transactionDebit, "DEBIT");
            accountRepository.save(payerAccount);

            addTo(request);

            System.out.println(request.getSuccessUrl());

            ResponseEntity<Object> exchange = restTemplate.exchange(request.getSuccessUrl(), HttpMethod.POST, requestEntity, Object.class);

            return exchange.getBody();
        }else {
            //prebaci zahtev na pcc
            RequestFromBank requestFromBank = new RequestFromBank();
            requestFromBank.setAcquirerTimestamp(date);
            requestFromBank.setCardHolderName(payment.getCardHolderName());
            requestFromBank.setExpirationDate(payment.getExpirationDate());
            requestFromBank.setSecurityCode(payment.getSecurityCode());
            requestFromBank.setPan(payment.getPan());
            requestFromBank.setAcquirerOrderId(ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L));
            //requestFromBankRepository.save(requestFromBank);

            Request request = requestRepository.findRequestByPaymentId(id);

            requestFromBank.setAmount(request.getAmount());
            //requestFromBankRepository.save(requestFromBank);

            ResponseEntity<ResponseFromBank> exchange = restTemplate.exchange(pccUrl+"/request", HttpMethod.POST, requestEntity, ResponseFromBank.class);
            String responseLink = "";
            if(exchange.getBody().getStatus().equals("Successful")){
                addTo(request);
                responseLink = request.getSuccessUrl();

            } else if(exchange.getBody().getStatus().equals("Error")){
                responseLink = request.getErrorUrl();

            } else if(exchange.getBody().getStatus().equals("Failed")){
                responseLink = request.getFailedUrl();
                return Collections.singletonMap("redirectUrl", request.getFailedUrl());
            }else
                responseLink = request.getErrorUrl();

            System.out.println("Link na koji treba red " + responseLink);
            responseFromBankRepository.save( exchange.getBody());
            ResponseEntity<Object> excRed = restTemplate.exchange(responseLink, HttpMethod.POST, requestEntity, Object.class);

            return excRed.getBody();
        }

    }

    private void addTo(Request request){

        Account clientAccount = merchantService.getAccountFromMerchant(
                request.getMerchantId(),
                request.getMerchantPassword());

        Transaction transactionCredit = new Transaction();
        transactionCredit.setAmount(request.getAmount());
        transactionCredit.setRequest(request);
        transactionCredit.setTransactionDate(new Date());
        transactionCredit.setAccount(clientAccount);

        clientAccount.setAmount(clientAccount.getAmount() + request.getAmount());

        transactionService.saveNew(transactionCredit, "CREDIT");
        accountRepository.save(clientAccount);
    }

    public ResponseFromBank payFromPcc(RequestFromBank requestFromBank){
        Account payerAccount = null;
        Date date = new Date();

        Account optionalAccount = accountService.getAccount(
                        requestFromBank.getPan(),
                        requestFromBank.getSecurityCode(),
                        requestFromBank.getCardHolderName(),
                        requestFromBank.getExpirationDate());


        payerAccount = optionalAccount;
        ResponseFromBank response = new ResponseFromBank();

        if (payerAccount == null)
            response.setStatus("Error");


        response.setACQUIRER_TIMESTAMP(requestFromBank.getAcquirerTimestamp());
        response.setISSUER_TIMESTAMP(new Date());
        response.setACQUIRER_ORDER_ID(requestFromBank.getAcquirerOrderId());
        response.setISSUER_ORDER_ID(ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L));

        if (payerAccount.getAmount() < requestFromBank.getAmount()) {
            response.setStatus("Failed");
        }else{
            response.setStatus("Successful");
        }

        Transaction transactionDebit = new Transaction();
        transactionDebit.setAmount(requestFromBank.getAmount());
        transactionDebit.setRequest(null);
        transactionDebit.setTransactionDate(new Date());
        transactionDebit.setAccount(payerAccount);
        payerAccount.setAmount(payerAccount.getAmount() - requestFromBank.getAmount());
        transactionService.saveNew(transactionDebit, "DEBIT");
        accountRepository.save(payerAccount);

        requestFromBankRepository.save(requestFromBank);

        return response;
    }

    private String generateRandomPaymentId() {
        String generatedString = UUID.randomUUID().toString();
        return generatedString;
    }

    private Boolean isRequestForMe(Payment payment) {
        if(payment.getPan().substring(0,6).equals(bin))
            return true;
        return false;
    }

    public Object cancel(String paymentId) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        Request request = requestRepository.findRequestByPaymentId(paymentId);

        String redirectUrl;

        redirectUrl = request.getFailedUrl();

        ResponseEntity<Object> excRed = restTemplate.exchange(redirectUrl, HttpMethod.POST, requestEntity, Object.class);

        return excRed.getBody();
    }

    public Object nemaNovacaBato(String paymentId){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);
        Request request = requestRepository.findRequestByPaymentId(paymentId);
        String redirectUrl = "";
        redirectUrl = request.getErrorUrl();

        ResponseEntity<Object> excRed = restTemplate.exchange(redirectUrl, HttpMethod.POST, requestEntity, Object.class);

        return excRed.getBody();
    }
}
