package com.microservice.bank_service.service;

import org.springframework.stereotype.Service;

@Service
public class TestServicePayment {

    public String testPayment(String test){

        //ResponseEntity<String> exchange = restTemplate.exchange("https://localhost:8762/payment-sc/testPaymentSc", HttpMethod.POST , requestEntity, String.class);
        
        /*System.out.println(exchange.getBody());

        String testService = " Test Service Payment return " ;*/

        return "OVO RADI IIIIII";//exchange.getBody();
    }


}
