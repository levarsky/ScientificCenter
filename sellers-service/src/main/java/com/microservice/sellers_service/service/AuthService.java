package com.microservice.sellers_service.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public Authentication getAuth(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication;
    }

}
