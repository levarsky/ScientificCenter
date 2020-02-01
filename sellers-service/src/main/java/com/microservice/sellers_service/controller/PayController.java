package com.microservice.sellers_service.controller;

import com.microservice.sellers_service.model.PaymentRequest;
import com.microservice.sellers_service.model.PaymentType;
import com.microservice.sellers_service.service.PaymentRequestService;
import com.microservice.sellers_service.service.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/pay")
public class PayController {

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private PaymentRequestService paymentRequestService;

    private String serverPath = "http://localhost:4201";

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String request(@RequestParam Double price) {
        System.out.println("Treba da se plati: " + price);
        return paymentRequestService.createRequest(price).getToken();
    }

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public void gePaymentTypes(@RequestParam(value = "token") String token, @RequestParam(value = "magazineName") String magazineName, @RequestParam(value = "magazineType") Long magazineType, @RequestParam(value = "userGivenName") String userGivenName, @RequestParam(value = "userSurname") String userSurname, @RequestParam(value = "userEmail") String userEmail, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect(serverPath+"/request?token="+token+"&magazineName=" + magazineName + "&magazineType=" + magazineType + "&userGivenName=" + userGivenName + "&userSurname=" + userSurname + "&userEmail=" + userEmail);
    }

    @RequestMapping(value = "/{price}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createCommunication(@PathVariable(value = "price") Double price) {
        System.out.println("Treba da se plati: " + price);
        return serverPath;
    }

    @RequestMapping(value = "/red", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public void get(HttpServletResponse httpServletResponse) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:https://localhost:8769");
        httpServletResponse.sendRedirect("http://localhost:4201");
    }

    @GetMapping(value = "/home")
    public String home(Model model) {
        return "forward:index.html";
    }

    @RequestMapping(value = "/paymentTypes", method = RequestMethod.GET)
    public ResponseEntity<List<PaymentType>> gePaymentTypes(@RequestParam(value ="token" ) String token) {
        return new ResponseEntity<>(paymentTypeService.getPaymentTypes(token), HttpStatus.OK);
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity<PaymentRequest> getTokenRequest(@RequestParam(value ="token" ) String token) {
        return new ResponseEntity<>(paymentRequestService.getRequest(token), HttpStatus.OK);
    }

    @RequestMapping(value="/paymentRequest",method = RequestMethod.GET)
    public ResponseEntity<?> sendPaymentRequest(@RequestParam String token,@RequestParam Long id, @RequestParam String magazineName, @RequestParam String magazineType, @RequestParam String userGivenName, @RequestParam String userSurname, @RequestParam String userEmail){
        return new ResponseEntity<>(paymentRequestService.sendPaymentRequest(token,id, magazineName, magazineType, userGivenName, userSurname, userEmail),HttpStatus.OK);
    }

}
