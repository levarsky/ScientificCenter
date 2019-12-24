package com.microservice.sellers_service.repository;

import com.microservice.sellers_service.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
}
