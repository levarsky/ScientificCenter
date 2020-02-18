package com.microservice.bitcoin_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.bitcoin_service.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
	public Client findByClientId(String clientId);
}
