package com.microservice.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public String testPayment(String test){
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(test);
//
//        //ResponseEntity<String> exchange = restTemplate.exchange("https://localhost:8765/testPayment", HttpMethod.POST , requestEntity, String.class);
//        //ResponseEntity<String> exchange = restTemplate.exchange("https://localhost:8088/sellers/testPayment", HttpMethod.POST, requestEntity, String.class);
//
//        ResponseEntity<String> exchange2 = restTemplate.getForEntity("https://localhost:8088/sellers/api/red",String.class);
//
//        System.out.println(exchange2.getBody());
//
//        return exchange2.getBody();
//    }


}
