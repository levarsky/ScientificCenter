package com.microservice.sellers_service.repository;

import com.microservice.sellers_service.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
        PaymentType findByServiceName(String serviceName);
}
