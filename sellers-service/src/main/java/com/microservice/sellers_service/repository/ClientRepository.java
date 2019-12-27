package com.microservice.sellers_service.repository;

import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByClientId(String clientId);
}
