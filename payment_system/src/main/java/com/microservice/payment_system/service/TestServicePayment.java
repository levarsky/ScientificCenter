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
    private RestTemplate restTemplateNoLoad;

    public String testPayment(String test){

        HttpEntity<String> requestEntity = new HttpEntity<>(test);

        ResponseEntity<String> exchange = restTemplateNoLoad.exchange("http://localhost:8768/testBank", HttpMethod.POST , requestEntity, String.class);

        System.out.println(exchange.getBody());

        String testService = " Test Service Payment return " ;

        return testService;
    }


}
