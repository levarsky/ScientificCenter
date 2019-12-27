package com.microservice.sellers_service.service;

import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.model.Request;
import com.microservice.sellers_service.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private ClientService clientService;

    public Request createRequest(String clientId,double amount){

        String token = UUID.randomUUID().toString();

        Request request = new Request();

        request.setAmount(amount);
        request.setClient(clientService.findByClientId(clientId));
        request.setToken(token);

        return requestRepository.save(request);
    }

    public Request getRequest(String token){
        return this.requestRepository.findByToken(token).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token does not exist"));

    }

}
