package com.microservice.sellers_service.controller;

import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.service.ClientService;
import com.microservice.sellers_service.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<?> registerClient(@RequestBody Client client) {
        return new ResponseEntity<>(this.registrationService.registerClient(client), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getClient() {
        return new ResponseEntity<>(clientService.getClient(), HttpStatus.OK);
    }



}
