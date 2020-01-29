package com.microservice.sellers_service.repository;

import com.microservice.sellers_service.model.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest,Long> {
    Optional<PaymentRequest> findByToken(String token);

    Optional<PaymentRequest> findByTransactionId(String transactionId);
}
