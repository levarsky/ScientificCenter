package com.microservice.payment_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestServicePayment {

    @Autowired
    private RestTemplate restTemplate;

    public String testPayment(String test){

        HttpEntity<String> requestEntity = new HttpEntity<>(test);

        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8762/payment_sc/testPaymentSc", HttpMethod.POST , requestEntity, String.class);
        
        /*System.out.println(exchange.getBody());

        String testService = " Test Service Payment return " ;*/

        return exchange.getBody();
    }


}
