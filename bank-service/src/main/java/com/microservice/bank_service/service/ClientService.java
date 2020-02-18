package com.microservice.bank_service.service;

import com.microservice.bank_service.communication.SellersClient;
import com.microservice.bank_service.model.Account;
import com.microservice.bank_service.model.Client;
import com.microservice.bank_service.model.MerchantDTO;
import com.microservice.bank_service.model.RegistrationRequest;
import com.microservice.bank_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RestTemplate restTemplateNotBalanced;

    @Autowired
    private OAuth2RestOperations restTemplate;

    @Autowired
    private RegistrationRequestService registrationRequestService;

    @Autowired
    private AuthService authService;

    @Autowired
    private SellersClient sellersClient;

    private String registrationForm = "http://localhost:4206/registrationRequest";

    private String bankUrl = "https://localhost:8768/merchant";

    public Client getClientById(String clientId){
        Optional<Client> clientOptional = clientRepository.findByClientId(clientId);
        if (!clientOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client doesn't exist!");
        return clientOptional.get();
    }

    public Object registerNewClient(Account account,String tokenId){

        System.out.println(account.getExpirationDate());

        String clientId = authService.getAuth().getPrincipal().toString();

        RegistrationRequest registrationRequest = registrationRequestService.getRegistrationRequest(clientId,tokenId);

        Optional<Client> optionalClient = clientRepository.findByClientId(registrationRequest.getClientId());

        String mode = registrationRequest.getMode();

        if (mode.equals("ADD")){

            if(optionalClient.isPresent()){

                sellersClient.clientSuccess(registrationRequest.getClientId(),"ERROR","bank-service");
            }

        }

        Client client = null;

        HttpEntity<?> requestEntity = new HttpEntity<>(account);

        ResponseEntity<MerchantDTO> exchange = restTemplateNotBalanced.exchange(bankUrl, HttpMethod.POST
                , requestEntity, MerchantDTO.class);

        MerchantDTO merchantDTO = exchange.getBody();

        System.out.println(mode);

        client = new Client();
        client.setMerchantId(merchantDTO.getMerchantId());
        client.setMerchantPassword(merchantDTO.getMerchantPassword());

        if (mode.equals("EDIT")){
            client.setId(optionalClient.get().getId());
        }else if (mode.equals("ADD")){
            client.setId(null);
            client.setClientId(registrationRequest.getClientId());
        }


        clientRepository.save(client);

        HashMap<String,Object> map = new HashMap<>();

        map.put("client",client);
        map.put("redirectUrl","https://localhost:4201/home/dashboard");

        sellersClient.clientSuccess(registrationRequest.getClientId(),"SUCCESSFUL","bank-service");

        return map;

    }


    public Object registerUrl(String clientId,String username,String mode) {

        RegistrationRequest registrationRequest = new RegistrationRequest();

        registrationRequest.setAccessed(false);
        registrationRequest.setClientId(clientId);
        registrationRequest.setUsername(username);
        registrationRequest.setMode(mode);

        String token = generateRandomPaymentId();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(registrationForm)
                .queryParam("tokenId",token);

        if(mode.equals("SHOW"))
            builder.queryParam("show",true);
        else if(mode.equals("EDIT"))
            builder.queryParam("edit",true);

        registrationRequest.setTokenId(token);

        registrationRequestService.save(registrationRequest);

        return Collections.singletonMap("redirectUrl",builder.build().encode().toUriString());
    }

    private String generateRandomPaymentId() {
        String generatedString = UUID.randomUUID().toString();
        return generatedString;
    }

    public Object checkUrl(String tokenId) {

        String clientId = authService.getAuth().getPrincipal().toString();

        System.out.print(clientId);

        RegistrationRequest registrationRequest = registrationRequestService.getRegistrationRequest(clientId,tokenId);

        if (registrationRequest.isAccessed()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid!");
        }

        registrationRequest.setAccessed(true);
        registrationRequestService.save(registrationRequest);

        Optional<Client> optionalClient = clientRepository.findByClientId(registrationRequest.getClientId());

        if(registrationRequest.getMode().equals("SHOW") || registrationRequest.getMode().equals("EDIT")){
            return optionalClient.get();
        }

        return null;
    }

    public Object sendStatus(String clientId,String status,String paymentService){

        String url = "https://localhost:8769/sellers/client/status";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("clientId",clientId)
                .queryParam("status",status)
                .queryParam("paymentService",paymentService);


        String paymentUrl = builder.build().encode().toUriString();

        System.out.println(paymentUrl);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<Object> exchange = restTemplate.exchange(paymentUrl, HttpMethod.GET, requestEntity, Object.class);

        return exchange.getBody();

    }
}
