package com.microservice.bank_service.service;

import com.microservice.bank_service.communication.SellersClient;
import com.microservice.bank_service.model.Account;
import com.microservice.bank_service.model.Client;
import com.microservice.bank_service.model.MerchantDTO;
import com.microservice.bank_service.model.RegistrationRequest;
import com.microservice.bank_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RestTemplate restTemplate;

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

        Optional<Client> optionalClient = clientRepository.findByClientId(clientId);

        String mode = registrationRequest.getMode();

        if (mode.equals("CREATE")){

            if(optionalClient.isPresent()){

                return sellersClient.clientSuccess(clientId,"ERROR","bank-service");
            }


        }

        Client client = null;

        HttpEntity<?> requestEntity = new HttpEntity<>(account);

        ResponseEntity<MerchantDTO> exchange = restTemplate.exchange(bankUrl, HttpMethod.POST
                , requestEntity, MerchantDTO.class);

        MerchantDTO merchantDTO = exchange.getBody();

        client = new Client();
        client.setMerchantId(merchantDTO.getMerchantId());
        client.setMerchantPassword(merchantDTO.getMerchantPassword());

        if (mode.equals("EDIT")){
            client.setId(optionalClient.get().getId());
        }else if (mode.equals("CREATE")){
            client.setId(null);
            client.setClientId(registrationRequest.getClientId());
        }


        clientRepository.save(client);

        return sellersClient.clientSuccess(clientId,"SUCCESSFUL","bank-service");

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

        registrationRequest.setTokenId(token);

        registrationRequestService.save(registrationRequest);

        return Collections.singletonMap("redirectUrl",builder.build().encode().toUriString());
    }

    private String generateRandomPaymentId() {
        String generatedString = UUID.randomUUID().toString();
        return generatedString;
    }

    public void checkUrl(String tokenId) {

        String clientId = authService.getAuth().getPrincipal().toString();

        System.out.print(clientId);

        RegistrationRequest registrationRequest = registrationRequestService.getRegistrationRequest(clientId,tokenId);

        if (registrationRequest.isAccessed()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid!");
        }

        registrationRequest.setAccessed(true);
        registrationRequestService.save(registrationRequest);

    }
}
