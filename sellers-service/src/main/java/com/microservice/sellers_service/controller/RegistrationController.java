package com.microservice.sellers_service.controller;


import com.microservice.sellers_service.model.User;
import com.microservice.sellers_service.service.Logging;
import com.microservice.sellers_service.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    private Logging logging = new Logging(getClass());

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@RequestBody User user, HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + user.getEmail() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: ");
        this.registrationService.createUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/confirm")
    public ResponseEntity<?> confirm(@RequestParam String token, HttpServletRequest hr) {
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + token);
        return new ResponseEntity<>(this.registrationService.confirm(token), HttpStatus.OK);
    }

}
