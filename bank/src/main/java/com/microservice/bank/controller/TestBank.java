package com.microservice.bank.controller;

import com.microservice.bank.service.TestServiceBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testBank")
public class TestBank {

    @Autowired
    private TestServiceBank testServiceBank;

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody String test) {

        return new ResponseEntity<>(testServiceBank.testBank(test), HttpStatus.OK);
    }

}
