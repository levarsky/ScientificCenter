package com.microservice.center.controller;

import com.microservice.center.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestSc {

    @Autowired
    private TestService testService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> create() {
        return new ResponseEntity<>(testService.testPayment("SC SEND MESSAGE"), HttpStatus.OK);
    }

}
