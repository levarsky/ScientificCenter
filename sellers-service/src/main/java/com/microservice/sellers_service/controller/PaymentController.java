package com.microservice.sellers_service.controller;

import com.microservice.sellers_service.model.PaymentStatus;
import com.microservice.sellers_service.service.AuthService;
import com.microservice.sellers_service.service.Logging;
import com.microservice.sellers_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    private Logging logging = new Logging(getClass());

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AuthService authService;

    @RequestMapping(value= "/status",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentSuccess(@RequestParam String transactionId,@RequestParam String paymentStatus, HttpServletRequest hr) throws Exception {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " +  transactionId + " " + paymentStatus);
        return new ResponseEntity<>(paymentService.paymentStatus(transactionId,paymentStatus), HttpStatus.OK);
    }







}
