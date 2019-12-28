package com.microservice.bank_service.controller;

import com.microservice.bank_service.model.PaymentRequest;
import com.microservice.bank_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping( method = RequestMethod.POST)
    public PaymentRequest create(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.pay(paymentRequest);
    }

    @RequestMapping(value="/successful/{id}", method = RequestMethod.POST)
    public void successful(@PathVariable(value = "id") Integer id) {
        paymentService.successful(id);
    }

    @RequestMapping(value="/failed/{id}", method = RequestMethod.POST)
    public void failed(@PathVariable(value = "id") Integer id) {
        paymentService.failed(id);
    }

    @RequestMapping(value="/error/{id}", method = RequestMethod.POST)
    public void error(@PathVariable(value = "id") Integer id) {
        paymentService.error(id);
    }


}
