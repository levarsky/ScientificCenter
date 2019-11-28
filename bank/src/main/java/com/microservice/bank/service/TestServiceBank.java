package com.microservice.bank.service;

import org.springframework.stereotype.Service;

@Service
public class TestServiceBank {

    public String testBank(String test){

        System.out.println("ovo:" + test);

        String testBank = " Test Service Bank return " ;

        return testBank;
    }

}
