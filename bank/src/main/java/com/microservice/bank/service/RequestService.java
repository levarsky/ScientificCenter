package com.microservice.bank.service;
import com.microservice.bank.model.*;
import com.microservice.bank.repository.AccountRepository;
import com.microservice.bank.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.*;

@Service
public class RequestService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private RestTemplate restTemplate;

    public Object generateResponse(Request request){

        String bankPaymentForm = "http://localhost:4205/paymentRequest";

        System.out.println(request.toString());

        System.out.println(request.getMerchantId());
        Account account = merchantService.getAccountFromMerchant(request.getMerchantId(),request.getMerchantPassword());


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

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(payment.getExpirationDate());
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        Account optionalAccount = accountService.getAccount(
                payment.getPan(), payment.getSecurityCode(),payment.getCardHolderName(),payment.getExpirationDate());


        payerAccount = optionalAccount;

        String redirectUrl;

        Request request = requestRepository.findRequestByPaymentId(id);
        if(request == null){
            redirectUrl=request.getErrorUrl();
            return Collections.singletonMap("redirectUrl",redirectUrl);
        }

        if(payerAccount == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid data!");

        if(payerAccount.getAmount() < request.getAmount()){
            redirectUrl=request.getFailedUrl();
            return Collections.singletonMap("redirectUrl",redirectUrl);
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

        transactionService.saveNew(transactionDebit,"DEBIT");
        accountService.saveAccount(payerAccount);


        Transaction transactionCredit = new Transaction();
        transactionCredit.setAmount(request.getAmount());
        transactionCredit.setRequest(request);
        transactionCredit.setTransactionDate(new Date());
        transactionCredit.setAccount(clientAccount);

        clientAccount.setAmount(clientAccount.getAmount() + request.getAmount());

        transactionService.saveNew(transactionCredit,"CREDIT");
        accountService.saveAccount(clientAccount);


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        System.out.println(request.getSuccessUrl());

        ResponseEntity<Object> exchange = restTemplate.exchange(request.getSuccessUrl(), HttpMethod.POST, requestEntity, Object.class);

        return exchange.getBody();
    }

    private String generateRandomPaymentId() {
        String generatedString = UUID.randomUUID().toString();
        return generatedString;
    }
}
