package com.microservice.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestServiceBank {

    @Autowired
    private RestTemplate restTemplate;

    public String testBank(String test){

        System.out.println(test);

        String testBank = " Test Service Bank return " ;

        return testBank;
    }

}
