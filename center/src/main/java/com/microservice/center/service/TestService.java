package com.microservice.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestService {

    @Autowired
    private RestTemplate restTemplate;

    public String testPayment(String test){

        HttpEntity<String> requestEntity = new HttpEntity<>(test);
        
        //ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8765/testPayment", HttpMethod.POST , requestEntity, String.class);
        ResponseEntity<String> exchange = restTemplate.exchange("https://localhost:8088/payment/testPayment", HttpMethod.POST, requestEntity, String.class);

        System.out.println(exchange.getBody());

        return exchange.getBody();
    }


}
