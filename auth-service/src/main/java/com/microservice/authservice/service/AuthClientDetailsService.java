package com.microservice.authservice.service;

import com.microservice.authservice.model.AuthClientDetails;
import com.microservice.authservice.repository.AuthClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class AuthClientDetailsService implements ClientDetailsService {

    @Autowired
    private AuthClientDetailsRepository authClientDetailsRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        AuthClientDetails authClientDetails = authClientDetailsRepository.findByClientId(clientId).orElseThrow(
                () -> new ClientRegistrationException(" Client not found with id : " + clientId));
        return authClientDetails;
    }
}
