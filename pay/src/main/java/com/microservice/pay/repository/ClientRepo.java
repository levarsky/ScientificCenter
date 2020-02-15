package com.microservice.pay.repository;

import com.microservice.pay.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long>{
	public Client findByClientId(String id);
}
