package com.microservice.registrationservice.controller;

import com.microservice.registrationservice.model.User;
import com.microservice.registrationservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> createUser(User user) {
        return new ResponseEntity<>(this.registrationService.createUser(user), HttpStatus.OK);
    }

}
