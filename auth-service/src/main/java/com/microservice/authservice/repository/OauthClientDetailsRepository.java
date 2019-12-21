package com.microservice.authservice.repository;

import com.microservice.authservice.model.OauthClientDetails;
import com.microservice.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, Long> {
    Optional<OauthClientDetails> findByClientId(String clientId);
}
