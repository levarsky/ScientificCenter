package com.microservice.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    @Autowired
    private RestTemplate restTemplate;

    public void pay(Double amount){
        System.out.println("Eto malo sam platio " + amount + "eur");
    }

    public String getToken(double amount){

        String clientId = "aXx2CcgHxZP_30XSS48XxbgV_m1nbS2o_ZcUCmhx__4";

        String url = "https://localhost:8088/sellers/pay/"+clientId+"/"+amount;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<String> exchange = restTemplate.exchange(url,HttpMethod.POST, requestEntity, String.class);

        return exchange.getBody();
    }


}
