package com.microservice.pay.repository;

import com.microservice.pay.model.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {
    Optional<RegistrationRequest> findByUsernameAndTokenId(String username, String tokenId);
}
