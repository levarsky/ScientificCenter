package com.microservice.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.pay.model.PaymentDetails;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails, Long>{
	
}
