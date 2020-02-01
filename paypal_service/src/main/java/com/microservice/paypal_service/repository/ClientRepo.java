package com.microservice.paypal_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.paypal_service.model.Client;

public interface ClientRepo extends JpaRepository<Client, Long>{
	public Client findByClientId(String id);
}
