package com.microservice.registrationservice.controller;

import com.microservice.registrationservice.model.OauthClientDetails;
import com.microservice.registrationservice.model.User;
import com.microservice.registrationservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return new ResponseEntity<>(this.registrationService.createUser(user), HttpStatus.OK);
    }

    @PostMapping(value="/client")
    public ResponseEntity<?> registerClient(@RequestBody OauthClientDetails oauthClientDetails) {
        return new ResponseEntity<>(this.registrationService.registerClient(oauthClientDetails), HttpStatus.OK);
    }

    @GetMapping(value="/confirm")
    public ResponseEntity<?> confirm(@RequestParam String token) {
        return new ResponseEntity<>(this.registrationService.confirm(token), HttpStatus.OK);
    }

}
