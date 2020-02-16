package com.microservice.center.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.microservice.center.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.center.service.MagazineService;
import com.microservice.center.service.PaymentService;
import com.microservice.center.service.TransactionService;
import com.microservice.center.service.UserService;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;
    
    @Autowired
    MagazineService magazineService;

    @RequestMapping(value= "/{amount}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pay(@PathVariable(value="amount") Double amount, @RequestBody List<Long> ids, HttpServletRequest hr) throws Exception {
        return new ResponseEntity<>(paymentService.pay(amount, ids, hr) ,HttpStatus.OK);
    }

    @RequestMapping(value= "/subscribe/{amount}/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> subscribe(@PathVariable(value="amount") Double amount,@PathVariable(value="id") Long id, HttpServletRequest hr) throws Exception {

        return new ResponseEntity<>(paymentService.subscribe(amount,id,hr), HttpStatus.OK);
    }

    @RequestMapping(value= "/success",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentSuccess(@RequestParam String requestId) throws Exception {
        Transaction transaction = transactionService.getByToken(requestId);
        if(transaction != null){
            transaction.setSuccess("successful");
            transactionService.save(transaction);
            userService.addFromTransactionToUser(transaction);
        }
        return new ResponseEntity<>(paymentService.paymentSuccess(requestId),HttpStatus.OK);
    }

    @RequestMapping(value= "/fail",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentFail(@RequestParam String requestId) throws Exception {
        Transaction transaction = transactionService.getByToken(requestId);
        if(transaction != null){
            transaction.setSuccess("failed");
            transactionService.save(transaction);
        }
        return new ResponseEntity<>(paymentService.paymentFail(requestId),HttpStatus.OK);
    }

    @RequestMapping(value= "/error",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentError(@RequestParam String requestId) throws Exception {
        Transaction transaction = transactionService.getByToken(requestId);
        if(transaction != null){
            transaction.setSuccess("error");
            transactionService.save(transaction);
        }
        return new ResponseEntity<>(paymentService.paymentError(requestId),HttpStatus.OK);
    }

    @RequestMapping(value= "/cancel",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentCancel(@RequestParam String requestId) throws Exception {
        Transaction transaction = transactionService.getByToken(requestId);
        if(transaction != null){
            transaction.setSuccess("canceled");
            transactionService.save(transaction);
        }
        return new ResponseEntity<>(paymentService.paymentCancel(requestId),HttpStatus.OK);
    }
}
