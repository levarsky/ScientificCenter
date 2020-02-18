package com.microservice.bitcoin_service.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public Authentication getAuth(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication;
    }

}
