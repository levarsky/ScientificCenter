package com.microservice.sellers_service.controller;


import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.model.User;
import com.microservice.sellers_service.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        this.registrationService.createUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/confirm")
    public ResponseEntity<?> confirm(@RequestParam String token) {
        return new ResponseEntity<>(this.registrationService.confirm(token), HttpStatus.OK);
    }

}
