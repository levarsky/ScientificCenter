package com.microservice.center.controller;

import com.microservice.center.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/{amount}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String testController(@PathVariable(value = "amount") Double amount){
        System.out.println("ETO TU SAM");
        paymentService.pay(amount);
        return "redirect:www.google.com";
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String test(){
        return "RADI";
    }

}
