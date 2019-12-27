package com.microservice.center.controller;

import com.microservice.center.model.Resp;
import com.microservice.center.service.HttpClient;
import com.microservice.center.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private HttpClient httpClient = new HttpClient();

    @RequestMapping(value= "/{amount}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resp> testController(@PathVariable(value="amount") Double amount) throws Exception {
        System.out.println("Stiglo da platim: " + amount);
        String s = (String)httpClient.sendGet("URL OD PAYMENTA NA SELLERSU");
        Resp r = new Resp(s);
        return new ResponseEntity<>(r ,HttpStatus.OK);
    }


}
