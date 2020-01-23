package com.microservice.bank_service.service;

import com.microservice.bank_service.model.Client;
import com.microservice.bank_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    public Client getClientById(String clientId){
        Optional<Client> clientOptional = clientRepository.findByClientId(clientId);
        if (!clientOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client doesn't exist!");
        return clientOptional.get();
    }

}
