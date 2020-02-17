package com.microservice.pay.repository;

import com.microservice.pay.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Long>{
	Optional<Client> findByClientId(String id);
}
