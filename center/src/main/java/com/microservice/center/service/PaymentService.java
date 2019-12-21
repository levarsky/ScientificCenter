package com.microservice.center.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public void pay(Double amount){
        System.out.println("Eto malo sam platio " + amount + "eur");
    }
}
