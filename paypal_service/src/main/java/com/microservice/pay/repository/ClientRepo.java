package com.microservice.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.pay.model.Client;

public interface ClientRepo extends JpaRepository<Client, Long>{
	public Client findByClientId(String id);
}
