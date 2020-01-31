package com.microservice.bank.controller;

import com.microservice.bank.model.*;
import com.microservice.bank.repository.RequestFromBankRepository;
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
        return new ResponseEntity<>(requestService.generateResponse(request),HttpStatus.OK);
    }

    @RequestMapping(value = "/pay",method = RequestMethod.POST)
    public ResponseEntity<?> pay(@RequestBody Payment payment, @RequestParam String paymentId) {
        return new ResponseEntity<>(requestService.pay(payment,paymentId),HttpStatus.OK);
    }

    @RequestMapping(value = "/pay_from_pcc",method = RequestMethod.POST)
    public ResponseEntity<ResponseFromBank> pay(@RequestBody RequestFromBank requestFromBank) {
        return new ResponseEntity<>(requestService.payFromPcc(requestFromBank),HttpStatus.OK);
    }

}
