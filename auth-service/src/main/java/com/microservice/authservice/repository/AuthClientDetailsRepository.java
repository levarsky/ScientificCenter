package com.microservice.authservice.repository;

import com.microservice.authservice.model.AuthClientDetails;
import com.microservice.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthClientDetailsRepository extends JpaRepository<AuthClientDetails, Long> {
    Optional<AuthClientDetails> findByClientId(String clientId);
}
