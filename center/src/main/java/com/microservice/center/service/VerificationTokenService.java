package com.microservice.center.service;


import com.microservice.center.model.User;
import com.microservice.center.model.VerificationToken;
import com.microservice.center.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.UUID;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserService userService;

    public VerificationToken createVerificationToken(String username){

        String token = UUID.randomUUID().toString();

        User user = userService.getUser(username);

        VerificationToken verificationToken = new VerificationToken(token,user);


        return verificationTokenRepository.save(verificationToken);

    }

    public String confirmToken(String token){

        VerificationToken verificationToken =verificationTokenRepository.findByVerificationToken(token);
        if (verificationToken == null) {
            throw new ResponseStatusException(  HttpStatus.BAD_REQUEST,  "INVALID Token!" );
        }

        User user = verificationToken.getUser();

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new ResponseStatusException(  HttpStatus.BAD_REQUEST,  "Token Expired!" );
        }

        user.setEnabled(true);
        user.setVerified(true);
        userService.save(user);

        return "Account activated successfully!";
    }




}
