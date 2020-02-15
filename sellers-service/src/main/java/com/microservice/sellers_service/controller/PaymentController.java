package com.microservice.sellers_service.controller;

import com.microservice.sellers_service.model.PaymentStatus;
import com.microservice.sellers_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value= "/status",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentSuccess(@RequestParam String transactionId,@RequestParam String paymentStatus) throws Exception {
        return new ResponseEntity<>(paymentService.paymentStatus(transactionId,paymentStatus), HttpStatus.OK);
    }





}
