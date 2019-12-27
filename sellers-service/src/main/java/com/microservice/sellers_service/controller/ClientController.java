package com.microservice.sellers_service.controller;

import com.microservice.sellers_service.model.PaymentType;
import com.microservice.sellers_service.service.PaymentTypeService;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.net.InetAddress;
import java.util.List;

@RestController(value="/pay")
public class ClientController {

    @Autowired
    private PaymentTypeService paymentTypeService;

    private String serverPath = "http://localhost:4200";

    @RequestMapping(value = "/{price}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createCommunication(@PathVariable(value = "price") Double price) {
        System.out.println("Treba da se plati: " + price);
        return serverPath;
    }

    @RequestMapping(value = "/red", method = RequestMethod.GET)
    public ModelAndView get() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:https://google.com");
        return modelAndView;
    }

    @RequestMapping(value = "/paymentTypes", method = RequestMethod.GET)
    public ResponseEntity<List<PaymentType>> gePaymentTypes() {
        return new ResponseEntity<>(paymentTypeService.getPaymentTypes(),HttpStatus.OK);
    }

}
