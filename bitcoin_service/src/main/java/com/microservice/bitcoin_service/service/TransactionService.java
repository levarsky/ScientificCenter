package com.microservice.bitcoin_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.bitcoin_service.model.Transaction;
import com.microservice.bitcoin_service.repository.TransactionRepository;

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
}
