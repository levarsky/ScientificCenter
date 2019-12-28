package com.microservice.bitcoin_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.bitcoin_service.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long>{
	
}
