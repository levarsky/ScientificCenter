package com.microservice.sellers_service.service;


import com.microservice.sellers_service.communication.AuthServiceClient;
import com.microservice.sellers_service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
public class RegistrationService {

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private AuthService authService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private OAuth2RestOperations restTemplate;

    @Autowired
    private PaymentTypeService paymentTypeService;


    public void createUser(User user) {


        HttpEntity<User> requestEntity = new HttpEntity<>(user);

        ResponseEntity<User> exchange = restTemplate.exchange("https://auth-service/uaa/user", HttpMethod.POST, requestEntity, User.class);

        exchange.getBody();

        //return this.authServiceClient.createUser(user);
    }

    public OauthClientDetails registerClient(Client client) {

        System.out.println(client.getClientName());

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

    public Object registerNewPaymentType(String serviceName,String mode){

        Client client = clientService.getClient();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("https://"+serviceName+"/client")
                .queryParam("clientId",client.getClientId())
                .queryParam("username",client.getUsername())
                .queryParam("mode",mode);

        String requestUrl = builder.build().encode().toUriString();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<Object> exchange = restTemplate.exchange(requestUrl,HttpMethod.GET,requestEntity,Object.class);

        return  exchange.getBody();

    }

    public Object regStatus(String clientId, String status , String paymentService) {

        System.out.println("CLIENT " +clientId);

        if(status.equals("SUCCESSFUL")){
          Client client = clientService.findByClientId(clientId);
          PaymentType paymentType = paymentTypeService.getByServiceName(paymentService);
          if (!client.getPaymentTypes().contains(paymentType)){
              System.out.println("NE SADRZI");
              client.getPaymentTypes().add(paymentType);
          }


          clientService.saveClientDB(client);
        }

        return Collections.singletonMap("redirectUrl","https://localhost:4201/home/dashboard");
    }

    public Object genClient() {

        OauthClientDetails oauthClientDetails = authServiceClient.registerClient();

        Client client = clientService.getClient();

        client.setClientId(oauthClientDetails.getClientId());

        clientService.saveClientDB(client);

        return oauthClientDetails;

    }
}
