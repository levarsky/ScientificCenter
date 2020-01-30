package com.microservice.center.controller;

import com.microservice.center.model.UserDTO;
import com.microservice.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.OK);
    }

    @GetMapping(value="/confirm")
    public ResponseEntity<?> confirm(@RequestParam String token) {
        return new ResponseEntity<>(this.userService.confirmToken(token), HttpStatus.OK);
    }


}
