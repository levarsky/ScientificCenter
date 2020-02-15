package com.microservice.pay.repository;

import com.microservice.pay.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails, Long>{
	
}
