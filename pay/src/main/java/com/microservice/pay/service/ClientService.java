package com.microservice.pay.service;

import com.microservice.pay.model.Client;
import com.microservice.pay.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
	@Autowired
	ClientRepo repo;
	
	public Client findByClientId(String id) {
		return repo.findByClientId(id);
	}
}
