package com.microservice.pay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.pay.model.Transaction;
import com.microservice.pay.repository.TransactionRepository;

@Service
public class TransactionService {
	@Autowired
	TransactionRepository transactionRepo;
	
	public void save(Transaction transaction) {
		transactionRepo.save(transaction);
	}
	
	public Transaction findByMerchantOrderId(String id) {
		return transactionRepo.findByMerchantOrderId(id);
	}
	
	public Transaction findByToken(String token) {
		return transactionRepo.findByToken(token);
	}
}
