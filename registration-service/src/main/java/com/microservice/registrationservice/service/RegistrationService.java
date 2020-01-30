package com.microservice.registrationservice.service;

import com.microservice.registrationservice.client.AuthServiceClient;
import com.microservice.registrationservice.model.OauthClientDetails;
import com.microservice.registrationservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RegistrationService {

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private OAuth2RestOperations restTemplate;

    public User createUser(User user) {
        return this.authServiceClient.createUser(user);
    }

    public OauthClientDetails registerClient(OauthClientDetails oauthClientDetails) {

//        return authServiceClient.registerClient(oauthClientDetails);

        HttpEntity<?> requestEntity = new HttpEntity<>(oauthClientDetails);

        ResponseEntity<OauthClientDetails> exchange =
                restTemplate.exchange("https://auth-service/uaa/client",HttpMethod.POST, requestEntity,OauthClientDetails.class);

        return exchange.getBody();

    }

    public String confirm(String token){

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("https://auth-service/uaa/user/confirm")
                .queryParam("token",token);

        String paymentUrl = builder.build().encode().toUriString();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<String> exchange = restTemplate.exchange(paymentUrl, HttpMethod.GET, requestEntity, String.class);

        return exchange.getBody();

    }

}
