package com.microservice.authservice.controller;

import com.microservice.authservice.model.OauthClientDetails;
import com.microservice.authservice.model.User;
import com.microservice.authservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public OauthClientDetails registerClient() {
        return clientService.createClient();
    }

}
