package com.microservice.bank.service;
import com.microservice.bank.model.*;
import com.microservice.bank.repository.AccountRepository;
import com.microservice.bank.repository.RequestFromBankRepository;
import com.microservice.bank.repository.RequestRepository;
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
    private RequestRepository requestRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private RequestFromBankRepository requestFromBankRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bank.pan}")
    private String myPan;

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

        if(isRequestForMe(payment)) {


            Optional<Account> optionalAccount = accountRepository.
                    findByCardNumberAndCvvAndCardHolderNameAndExpirationDateIsGreaterThanEqual(
                            payment.getPan(),
                            payment.getSecurityCode(),
                            payment.getCardHolderName(),
                            payment.getExpirationDate());


            payerAccount = optionalAccount.get();

            String redirectUrl;

            Request request = requestRepository.findRequestByPaymentId(id);
            if (request == null) {
                redirectUrl = request.getErrorUrl();
                return Collections.singletonMap("redirectUrl", redirectUrl);
            }

            if (payerAccount == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data!");

            if (payerAccount.getAmount() < request.getAmount()) {
                redirectUrl = request.getFailedUrl();
                return Collections.singletonMap("redirectUrl", redirectUrl);
            }

            Account clientAccount = merchantService.getAccountFromMerchant(
                    request.getMerchantId(),
                    request.getMerchantPassword());


            Transaction transactionDebit = new Transaction();

            transactionDebit.setAmount(request.getAmount());
            transactionDebit.setRequest(request);
            transactionDebit.setTransactionDate(new Date());
            transactionDebit.setAccount(payerAccount);

            payerAccount.setAmount(payerAccount.getAmount() - request.getAmount());

            transactionService.saveNew(transactionDebit, "DEBIT");
            accountRepository.save(payerAccount);


            Transaction transactionCredit = new Transaction();
            transactionCredit.setAmount(request.getAmount());
            transactionCredit.setRequest(request);
            transactionCredit.setTransactionDate(new Date());
            transactionCredit.setAccount(clientAccount);

            clientAccount.setAmount(clientAccount.getAmount() + request.getAmount());

            transactionService.saveNew(transactionCredit, "CREDIT");
            accountRepository.save(clientAccount);


            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

            System.out.println(request.getSuccessUrl());

            ResponseEntity<Object> exchange = restTemplate.exchange(request.getSuccessUrl(), HttpMethod.POST, requestEntity, Object.class);

            return exchange.getBody();
        }else {
            //prebaci zahtev na pcc
            RequestFromBank requestFromBank = new RequestFromBank();
            requestFromBank.setACQUIRER_TIMESTAMP(date);
            requestFromBank.setCARD_HOLDER_NAME(payment.getCardHolderName());
            requestFromBank.setEXPIRATION_DATE(payment.getExpirationDate());
            requestFromBank.setSECURITY_CODE(payment.getSecurityCode());
            requestFromBank.setPAN(payment.getPan());
            requestFromBank.setACQUIRER_ORDER_ID(ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L));
            requestFromBankRepository.save(requestFromBank);

            String redirectUrl;

            Request request = requestRepository.findRequestByPaymentId(id);
            if (request == null) {
                redirectUrl = request.getErrorUrl();
                return Collections.singletonMap("redirectUrl", redirectUrl);
            }

            requestFromBank.setAmount(request.getAmount());
            requestFromBankRepository.save(requestFromBank);

            HttpEntity<?> requestEntity = new HttpEntity<>(requestFromBank);
            ResponseEntity<ResponseFromBank> exchange = restTemplate.exchange(pccUrl, HttpMethod.POST, requestEntity, ResponseFromBank.class);
            if(exchange.getBody().getStatus().equals("Successful"))
                return exchange.getBody();
            else if(exchange.getBody().getStatus().equals("Error"))
                return Collections.singletonMap("redirectUrl", request.getErrorUrl());
            else if(exchange.getBody().getStatus().equals("Failed"))
                return Collections.singletonMap("redirectUrl", request.getFailedUrl());

            return Collections.singletonMap("redirectUrl", request.getErrorUrl());
        }

    }

    public ResponseFromBank payFromPcc(RequestFromBank requestFromBank){
        Account payerAccount = null;
        Date date = new Date();

        Optional<Account> optionalAccount = accountRepository.
                findByCardNumberAndCvvAndCardHolderNameAndExpirationDateIsGreaterThanEqual(
                        requestFromBank.getPAN(),
                        requestFromBank.getSECURITY_CODE(),
                        requestFromBank.getCARD_HOLDER_NAME(),
                        requestFromBank.getEXPIRATION_DATE());


        payerAccount = optionalAccount.get();
        ResponseFromBank response = new ResponseFromBank();

        if (payerAccount == null)
            response.setStatus("Error");


        response.setACQUIRER_TIMESTAMP(requestFromBank.getACQUIRER_TIMESTAMP());
        response.setISSUER_TIMESTAMP(new Date());
        response.setACQUIRER_ORDER_ID(requestFromBank.getACQUIRER_ORDER_ID());
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

        return response;
    }

    private String generateRandomPaymentId() {
        String generatedString = UUID.randomUUID().toString();
        return generatedString;
    }

    private Boolean isRequestForMe(Payment payment) {
        if(payment.getPan().equals(myPan))
            return true;
        return false;
    }
}
