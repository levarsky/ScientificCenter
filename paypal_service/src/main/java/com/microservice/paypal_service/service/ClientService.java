package com.microservice.paypal_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.paypal_service.model.Client;
import com.microservice.paypal_service.repository.ClientRepo;

@Service
public class ClientService {
	@Autowired
	ClientRepo repo;
	
	public Client findByClientId(String id) {
		return repo.findByClientId(id);
	}
}
