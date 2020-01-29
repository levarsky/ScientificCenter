package com.microservice.bank.service;
import com.microservice.bank.model.Account;
import com.microservice.bank.model.Payment;
import com.microservice.bank.model.Request;
import com.microservice.bank.model.Response;
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
    private AccountRepository accountRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Object generateResponse(Request request){

        String bankPaymentForm = "http://localhost:4205/paymentRequest";



        System.out.println(request.toString());

        System.out.println(request.getMerchantId());
        Account account = accountRepository.findByMerchantIdAndMerchantPassword(request.getMerchantId(),request.getMerchantPassword());


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
        List<Account> accounts = accountRepository.findAll();
        Account pom = null;
        Date date = new Date();
        for(Account a : accounts){
            if(a.getCardNumber().equals(payment.getPan()) && a.getCvv().equals(payment.getSecurityCode()) &&
                a.getCardHolderName().equals(payment.getCardHolderName()) && payment.getExpirationDate().after(date)){
                pom = a;
            }
        }

        String redirectUrl;

        Request request = requestRepository.findRequestByPaymentId(id);
        if(request == null){
            redirectUrl=request.getErrorUrl();
            return Collections.singletonMap("redirectUrl",redirectUrl);
        }

        if(pom == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid data!");

        if(pom.getAmount() < request.getAmount()){
            redirectUrl=request.getFailedUrl();
            return Collections.singletonMap("redirectUrl",redirectUrl);
        }

        Account prodavac = accountRepository.findByMerchantIdAndMerchantPassword(request.getMerchantId(),request.getMerchantPassword());

        pom.setAmount(pom.getAmount() - request.getAmount());
        prodavac.setAmount(prodavac.getAmount() + request.getAmount());

        accountRepository.save(pom);

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