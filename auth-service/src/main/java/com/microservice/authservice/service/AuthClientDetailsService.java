package com.microservice.authservice.service;

import com.microservice.authservice.model.OauthClientDetails;
import com.microservice.authservice.repository.OauthClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class AuthClientDetailsService implements ClientDetailsService {

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetails oauthClientDetails = oauthClientDetailsRepository.findByClientId(clientId).orElseThrow(
                () -> new ClientRegistrationException(" Client not found with id : " + clientId));
        return oauthClientDetails;
    }
}
