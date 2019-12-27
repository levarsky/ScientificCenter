package com.microservice.sellers_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestService {
//	@Autowired
//    private RestTemplate restTemplate;
//
//	public String testPaymentSc(String test){
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(test);
//
//        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8768/testBank", HttpMethod.POST , requestEntity, String.class);
//
//        return exchange.getBody();
//    }
}
