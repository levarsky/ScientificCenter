package com.microservice.bank.controller;

import com.microservice.bank.model.Payment;
import com.microservice.bank.model.Request;
import com.microservice.bank.model.Response;
import com.microservice.bank.service.RequestService;
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
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<Response> create(@RequestBody Request request) {

        return new ResponseEntity<>(requestService.generateResponse(request),HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<?> pay(@RequestBody Payment payment) {
        requestService.pay(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
