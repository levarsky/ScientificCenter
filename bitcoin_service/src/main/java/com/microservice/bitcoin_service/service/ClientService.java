package com.microservice.bitcoin_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.bitcoin_service.model.Client;
import com.microservice.bitcoin_service.repository.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	ClientRepository clientRepo;
	
	public Client findByClientId(String clientId) {
		return clientRepo.findByClientId(clientId);
	}
}
