package com.microservice.registrationservice.service;

import com.microservice.registrationservice.client.AuthServiceClient;
import com.microservice.registrationservice.model.OauthClientDetails;
import com.microservice.registrationservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private AuthServiceClient authServiceClient;

    public User createUser(User user) {
        return this.authServiceClient.createUser(user);
    }

    public OauthClientDetails registerClient(OauthClientDetails oauthClientDetails) {
        return this.authServiceClient.registerClient(oauthClientDetails);
    }

}
