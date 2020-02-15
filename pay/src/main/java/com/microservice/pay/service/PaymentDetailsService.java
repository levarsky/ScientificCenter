package com.microservice.pay.service;

import com.microservice.pay.model.PaymentDetails;
import com.microservice.pay.repository.PaymentDetailsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsService {
	@Autowired
	private PaymentDetailsRepo repo;
	
	public void save(PaymentDetails payment) {
		repo.save(payment);
	}
}
