package com.microservice.sellers_service.service;


import com.microservice.sellers_service.communication.AuthServiceClient;
import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.model.OauthClientDetails;
import com.microservice.sellers_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RegistrationService {

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private ClientService clientService;

    @Autowired
    private OAuth2RestOperations restTemplate;


    public void createUser(User user) {


        HttpEntity<User> requestEntity = new HttpEntity<>(user);

        ResponseEntity<User> exchange = restTemplate.exchange("https://auth-service/uaa/user", HttpMethod.POST, requestEntity, User.class);

        exchange.getBody();

        //return this.authServiceClient.createUser(user);
    }

    public OauthClientDetails registerClient(Client client) {

        OauthClientDetails oauthClientDetails = authServiceClient.registerClient();

//        ResponseEntity<OauthClientDetails> exchange =
//                restTemplate.exchange("https://auth-service/uaa/client",HttpMethod.POST, requestEntity,OauthClientDetails.class);

//        return exchange.getBody();

        client.setClientId(oauthClientDetails.getClientId());
        clientService.saveClient(client);

        return oauthClientDetails;

    }

    public String confirm(String token){

        return authServiceClient.confirm(token);

//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl("https://auth-service/uaa/user/confirm")
//                .queryParam("token",token);
//
//        String paymentUrl = builder.build().encode().toUriString();
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
//
//        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);
//
//        ResponseEntity<String> exchange = restTemplate.exchange(paymentUrl, HttpMethod.GET, requestEntity, String.class);
//
//        return exchange.getBody();

    }

}
