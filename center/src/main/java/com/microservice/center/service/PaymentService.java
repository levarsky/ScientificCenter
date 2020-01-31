package com.microservice.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PaymentService {

    @Autowired
    private OAuth2RestOperations restTemplateOauth;

    @Value("${sellers.api}")
    private String sellersUrl;

    @Value("${center.front}")
    private String frontUrl;

    public String getToken(double amount){

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(sellersUrl)
                .queryParam("price", amount);

        System.out.println(builder.build().encode().toUri());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);


        System.out.println(restTemplateOauth.getAccessToken());

        ResponseEntity<String> exchange = restTemplateOauth.exchange(builder.build().encode().toUri(),HttpMethod.POST, requestEntity, String.class);

        System.out.println(exchange.toString());

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

    public String paymentCancel(String requestId){
        return frontUrl+"?cancel="+requestId;
    }



}
