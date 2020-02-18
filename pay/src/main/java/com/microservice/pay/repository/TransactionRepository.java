package com.microservice.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.pay.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	public Transaction findByMerchantOrderId(String id);
	public Transaction findByToken(String token);
}
