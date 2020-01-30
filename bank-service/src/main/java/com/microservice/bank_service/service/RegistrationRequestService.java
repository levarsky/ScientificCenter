package com.microservice.bank_service.service;

import com.microservice.bank_service.model.RegistrationRequest;
import com.microservice.bank_service.repository.RegistrationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RegistrationRequestService {

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    public RegistrationRequest getRegistrationRequest(String clientId,String tokenId){

        Optional<RegistrationRequest> optionalRegistrationRequest = registrationRequestRepository.findByUsernameAndTokenId(clientId,tokenId);
        if (!optionalRegistrationRequest.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid!");
        }

        return optionalRegistrationRequest.get();

    }

    public void save(RegistrationRequest registrationRequest) {
        registrationRequestRepository.save(registrationRequest);
    }
}
