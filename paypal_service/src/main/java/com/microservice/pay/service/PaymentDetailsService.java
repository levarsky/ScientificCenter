package com.microservice.pay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.pay.model.PaymentDetails;
import com.microservice.pay.repository.PaymentDetailsRepo;

@Service
public class PaymentDetailsService {
	@Autowired
	private PaymentDetailsRepo repo;
	
	public void save(PaymentDetails payment) {
		repo.save(payment);
	}
}
