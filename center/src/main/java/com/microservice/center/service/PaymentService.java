package com.microservice.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
public class PaymentService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${sellers.api}")
    private String sellersUrl;

    @Value("${sellers.security.clientId}")
    private String clientId;

    @Value("${center.front}")
    private String frontUrl;

    public String getToken(double amount){

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(sellersUrl)
                .queryParam("clientId", clientId)
                .queryParam("price", amount);


        System.out.println(builder.build().encode().toUri());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<String> exchange = restTemplate.exchange(builder.build().encode().toUri(),HttpMethod.POST, requestEntity, String.class);


        return exchange.getBody();
    }

    public String paymentSuccess(String requestId){
        return frontUrl+"?success="+requestId;
    }

    public String paymentFail(String requestId){
        return frontUrl+"?fail="+requestId;
    }

    public String paymentError(String requestId){
        return frontUrl+"?error="+requestId;
    }



}
