package com.microservice.bank_service.repository;

import com.microservice.bank_service.model.RegistrationRequest;
import com.microservice.bank_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {
    Optional<RegistrationRequest> findByUsernameAndTokenId(String username, String tokenId);
}
