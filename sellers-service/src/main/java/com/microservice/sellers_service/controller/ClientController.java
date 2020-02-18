package com.microservice.sellers_service.controller;

import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.service.AuthService;
import com.microservice.sellers_service.service.ClientService;
import com.microservice.sellers_service.service.Logging;
import com.microservice.sellers_service.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

    private Logging logging = new Logging(getClass());

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<?> registerClient(@RequestBody Client client, HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + client.getClientId());
        return new ResponseEntity<>(this.registrationService.registerClient(client), HttpStatus.OK);
    }

    @GetMapping(value="/genClient")
    public ResponseEntity<?> genClient(HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: ");
        return new ResponseEntity<>(this.registrationService.genClient(), HttpStatus.OK);
    }

    @PostMapping(value="/editClient")
    public ResponseEntity<?> editClient(@RequestBody Client client, HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + client.getClientId());
        return new ResponseEntity<>(this.clientService.edit(client), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getClient(HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: ");
        return new ResponseEntity<>(clientService.getClient(), HttpStatus.OK);
    }

    @GetMapping(value="/registerPayment")
    public ResponseEntity<?> getRegistrationUrl(@RequestParam String serviceName,@RequestParam String mode, HttpServletRequest hr){
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + serviceName + " " + mode);
        return new ResponseEntity<>(registrationService.registerNewPaymentType(serviceName,mode),HttpStatus.OK);
    }

    @RequestMapping(value= "/status",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> clientSuccess(@RequestParam String clientId,@RequestParam String status,@RequestParam String paymentService, HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + authService.getAuth().getPrincipal().toString() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + clientId + " " + status + " " + paymentService);
        return new ResponseEntity<>(registrationService.regStatus(clientId,status,paymentService), HttpStatus.OK);
    }
}
