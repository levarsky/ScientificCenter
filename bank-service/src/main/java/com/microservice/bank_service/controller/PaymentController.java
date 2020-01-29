package com.microservice.bank_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.bank_service.model.Client;
import com.microservice.bank_service.model.PaymentRequest;
import com.microservice.bank_service.service.PaymentService;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping( method = RequestMethod.POST)
    public PaymentRequest create(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.pay(paymentRequest);
    }

    @RequestMapping(value="/proba/{clientId}/{amount}", method = RequestMethod.GET)
    public PaymentRequest proba(@PathVariable(value = "clientId") String clientId, @PathVariable(value = "amount") Double amount) {
        PaymentRequest pr = new PaymentRequest();
        Client client = new Client();
        client.setClientId(clientId);
        pr.setClient(client);
        pr.setAmount(amount);
    	return paymentService.pay(pr);
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
