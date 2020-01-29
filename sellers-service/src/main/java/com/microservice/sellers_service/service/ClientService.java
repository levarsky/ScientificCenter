package com.microservice.sellers_service.service;

import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ClientService {


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




}
