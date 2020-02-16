package com.microservice.sellers_service.repository;

import com.microservice.sellers_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByPaymentRequestId(Long id);

}
