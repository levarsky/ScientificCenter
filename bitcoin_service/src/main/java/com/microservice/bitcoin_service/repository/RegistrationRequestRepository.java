package com.microservice.bitcoin_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.bitcoin_service.model.RegistrationRequest;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {
    Optional<RegistrationRequest> findByUsernameAndTokenId(String username, String tokenId);
}
