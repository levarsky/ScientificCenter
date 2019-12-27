package com.microservice.registrationservice.controller;

import com.microservice.registrationservice.model.OauthClientDetails;
import com.microservice.registrationservice.model.User;
import com.microservice.registrationservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(User user) {
        return new ResponseEntity<>(this.registrationService.createUser(user), HttpStatus.OK);
    }

    @PostMapping(value="/client")
    public ResponseEntity<?> registerClient(OauthClientDetails oauthClientDetails) {
        return new ResponseEntity<>(this.registrationService.registerClient(oauthClientDetails), HttpStatus.OK);
    }

}
