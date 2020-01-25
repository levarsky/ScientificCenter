package com.microservice.bank_service.controller;

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

    @RequestMapping(value="/successful/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> successful(@PathVariable(value = "id") Long id) {

        return new ResponseEntity<>(paymentService.successful(id),HttpStatus.OK);
    }

    @RequestMapping(value="/failed/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> failed(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(paymentService.failed(id),HttpStatus.OK);
    }

    @RequestMapping(value="/error/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> error(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(paymentService.error(id), HttpStatus.OK);

    }


}
