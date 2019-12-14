package com.microservice.authservice.repository;

import com.microservice.authservice.model.User;
import com.microservice.authservice.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByVerificationToken(String token);

    VerificationToken findByUser(User user);
}
