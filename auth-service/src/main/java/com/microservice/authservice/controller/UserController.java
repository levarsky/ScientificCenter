package com.microservice.authservice.controller;

import com.microservice.authservice.model.User;
import com.microservice.authservice.model.UserDTO;
import com.microservice.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current")
    public Principal getUser(Principal principal) {
        return principal;
    }

    @PostMapping
    public void registerUser(@RequestBody UserDTO userDTO) {
         userService.saveUser(userDTO);
    }

    @RequestMapping(value = "/confirm",method = RequestMethod.GET)
    public String confirm(@RequestParam(value = "token") String token){
        return  userService.confirmToken(token);
    }


}
