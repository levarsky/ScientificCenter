package com.microservice.sellers_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.sellers_service.service.TestService;

@RestController
@RequestMapping("/testPaymentSc")
public class TestController {
	@Autowired
	private TestService testService;
	
	@RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody String test) {
        return new ResponseEntity<>("TEST", HttpStatus.OK);
    }
}
