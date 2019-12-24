package com.microservice.sellers_service.repository;

import com.microservice.sellers_service.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
