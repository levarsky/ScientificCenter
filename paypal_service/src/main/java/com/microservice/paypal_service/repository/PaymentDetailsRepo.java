package com.microservice.paypal_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.paypal_service.model.PaymentDetails;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails, Long>{
	
}
