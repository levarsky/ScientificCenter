package com.microservice.paypal_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.paypal_service.model.PaymentDetails;
import com.microservice.paypal_service.repository.PaymentDetailsRepo;

@Service
public class PaymentDetailsService {
	@Autowired
	private PaymentDetailsRepo repo;
	
	public void save(PaymentDetails payment) {
		repo.save(payment);
	}
}
