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
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.pay(paymentRequest), HttpStatus.OK);
    }

    @RequestMapping(value="/proba/{clientId}/{amount}", method = RequestMethod.GET)
    public PaymentRequest proba(@PathVariable(value = "clientId") String clientId, @PathVariable(value = "amount") Double amount) {
        PaymentRequest pr = new PaymentRequest();
        Client client = new Client();
        client.setClientId(clientId);
        pr.setClient(client);
        pr.setAmount(amount);
    	return (PaymentRequest)paymentService.pay(pr);
    }
    
    @RequestMapping(value="/successful/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> successful(@PathVariable(value = "id") Long id) {

        return new ResponseEntity<>(paymentService.paymentStatus(id,"SUCCESSFUL"),HttpStatus.OK);
    }

    @RequestMapping(value="/failed/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> failed(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(paymentService.paymentStatus(id,"FAILED"),HttpStatus.OK);
    }

    @RequestMapping(value="/error/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> error(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(paymentService.paymentStatus(id,"ERROR"), HttpStatus.OK);

    }


}
