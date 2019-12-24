package com.microservice.authservice.service;

import com.microservice.authservice.model.OauthClientDetails;
import com.microservice.authservice.repository.OauthClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;

    public OauthClientDetails createClient() {

        OauthClientDetails oauthClientDetails = new OauthClientDetails();

        return oauthClientDetails;

    }


}
