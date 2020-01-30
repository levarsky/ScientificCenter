package com.microservice.center.repository;


import com.microservice.center.model.User;
import com.microservice.center.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByVerificationToken(String token);

    VerificationToken findByUser(User user);
}
