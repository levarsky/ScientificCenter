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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Request request) {
        requestService.generateResponse(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    public ResponseEntity<?> pay(@RequestBody Payment payment, @PathVariable(value = "id") String id) {
        requestService.pay(payment,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
