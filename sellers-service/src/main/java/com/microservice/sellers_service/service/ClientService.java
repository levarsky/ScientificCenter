package com.microservice.sellers_service.service;

import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private AuthService authService;


    @Autowired
    private ClientRepository clientRepository;

    public Client findById(Long id){

        return clientRepository.findById(id).orElseThrow
                (()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client does not exist"));

    }

    public Client findByClientId(String clientId){

        return clientRepository.findByClientId(clientId).orElseThrow
                (()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client does not exist"));

    }

    public Client saveClientDB(Client client){
        return clientRepository.save(client);
    }

    public Client getClient(){

        Authentication authentication = authService.getAuth();

        Optional<Client> client = clientRepository.findByUsername(authentication.getPrincipal().toString());

        if(!client.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not registered!");
        }

        return client.get();
    }

    public void saveClient(Client client){

        Authentication authentication = authService.getAuth();

        client.setRegistrationDate(new Date());

        System.out.println(authentication.getPrincipal());
        client.setUsername(authentication.getPrincipal().toString());
        saveClientDB(client);

    }


    public Object edit(Client client) {

        Client clientDB = getClient();

        clientDB.setClientName(client.getClientName());
        clientDB.setErrorUrl(client.getErrorUrl());
        clientDB.setSuccessUrl(client.getSuccessUrl());
        clientDB.setFailedUrl(client.getFailedUrl());

        return saveClientDB(clientDB);
    }

}
