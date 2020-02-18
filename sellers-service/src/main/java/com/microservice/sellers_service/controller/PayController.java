package com.microservice.sellers_service.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microservice.sellers_service.service.AuthService;
import com.microservice.sellers_service.service.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.microservice.sellers_service.model.PaymentDTO;
import com.microservice.sellers_service.model.PaymentRequest;
import com.microservice.sellers_service.model.PaymentType;
import com.microservice.sellers_service.model.Resp;
import com.microservice.sellers_service.service.PaymentRequestService;
import com.microservice.sellers_service.service.PaymentTypeService;

@RestController
@RequestMapping(value = "/pay")
public class PayController {

    private Logging logging = new Logging(getClass());

    @Autowired
    private AuthService authService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private PaymentRequestService paymentRequestService;

    private String serverPath = "http://localhost:4201";

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String request(@RequestBody PaymentDTO paymentDTO, HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + paymentDTO.toString());
        return paymentRequestService.createRequest(paymentDTO).getToken();
    }

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public void gePaymentTypes(@RequestParam(value = "token") String token,HttpServletResponse httpServletResponse, HttpServletRequest hr) throws IOException {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + token);
        httpServletResponse.sendRedirect(serverPath+"/request?token="+token);
    }


    @RequestMapping(value = "/{price}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createCommunication(@PathVariable(value = "price") Double price, HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + price);
        return serverPath;
    }

    @RequestMapping(value = "/red", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public void get(HttpServletResponse httpServletResponse, HttpServletRequest hr) throws IOException {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: ");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:https://localhost:8769");
        httpServletResponse.sendRedirect("http://localhost:4201");
    }

    @GetMapping(value = "/home")
    public String home(Model model, HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: ");
        return "forward:index.html";
    }

    @RequestMapping(value = "/paymentTypes", method = RequestMethod.GET)
    public ResponseEntity<List<PaymentType>> getPaymentTypes(@RequestParam(value ="token" ) String token, HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + token);
        return new ResponseEntity<>(paymentTypeService.getPaymentTypes(token), HttpStatus.OK);
    }

    @RequestMapping(value = "/clientPaymentTypes", method = RequestMethod.GET)
    public ResponseEntity<List<PaymentType>> getClientPaymentTypes(HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: ");
        return new ResponseEntity<>(paymentTypeService.getPaymentByClient(), HttpStatus.OK);
    }

    @RequestMapping(value = "/allPaymentTypes", method = RequestMethod.GET)
    public ResponseEntity<List<PaymentType>> allPaymentTypes(HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: ");
        return new ResponseEntity<>(paymentTypeService.getNotAdded(), HttpStatus.OK);
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity<PaymentRequest> getTokenRequest(@RequestParam(value ="token" ) String token, HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + token);
        return new ResponseEntity<>(paymentRequestService.getRequest(token), HttpStatus.OK);
    }

    @RequestMapping(value="/paymentRequest",method = RequestMethod.GET)
    public ResponseEntity<Object> sendPaymentRequest(@RequestParam String token,@RequestParam Long id, HttpServletRequest hr){
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + token + " " + id);
        return new ResponseEntity<>(paymentRequestService.sendPaymentRequest(token,id),HttpStatus.OK);
    }

}
