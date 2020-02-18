package com.microservice.bank_service.controller;

import com.microservice.bank_service.model.Account;
import com.microservice.bank_service.service.ClientService;
import com.microservice.bank_service.service.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/client")
public class ClientController {

    private Logging logging = new Logging(getClass());

    @Autowired
    private ClientService clientService;

    @GetMapping(value="/checkUrl")
    public ResponseEntity<?> checkUrl(@RequestParam String tokenId, HttpServletRequest hr){
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + tokenId);
        return new ResponseEntity<>(  clientService.checkUrl(tokenId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registerClient(@RequestParam String tokenId, @RequestBody Account account, HttpServletRequest hr){
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + tokenId);
        return new ResponseEntity<>(clientService.registerNewClient(account,tokenId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> registerRedirect(@RequestParam String clientId,@RequestParam String username,@RequestParam String mode, HttpServletRequest hr){
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + clientId + " " + username + " " + mode);
        return new ResponseEntity<>(clientService.registerUrl(clientId,username,mode), HttpStatus.OK);
    }




}
