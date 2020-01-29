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

    @RequestMapping(value= "/{amount}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resp> testController(@PathVariable(value="amount") Double amount) throws Exception {

        String requestToken = this.paymentService.getToken(amount);
        System.out.println(requestToken);
        String url = "https://localhost:8088/sellers/pay/request?token="+requestToken;
        Resp r = new Resp(url);
        return new ResponseEntity<>(r ,HttpStatus.OK);
    }

    @RequestMapping(value= "/success",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentSuccess(@RequestParam String requestId) throws Exception {
        return new ResponseEntity<>(paymentService.paymentSuccess(requestId),HttpStatus.OK);
    }

    @RequestMapping(value= "/fail",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentFail(@RequestParam String requestId) throws Exception {
        return new ResponseEntity<>(paymentService.paymentFail(requestId),HttpStatus.OK);
    }

    @RequestMapping(value= "/error",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentError(@RequestParam String requestId) throws Exception {
        return new ResponseEntity<>(paymentService.paymentError(requestId),HttpStatus.OK);
    }




}
