package com.microservice.payment_system.controller;


import com.microservice.payment_system.service.TestServicePayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testPayment")
public class TestPayment {

    @Autowired
    private TestServicePayment testServicePayment;

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody String test) {

        return new ResponseEntity<>(testServicePayment.testPayment(test), HttpStatus.OK);
    }


}
