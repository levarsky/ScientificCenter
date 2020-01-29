package com.microservice.bank_service.repository;

import com.microservice.bank_service.model.Client;
import com.microservice.bank_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByMerchantOrderId(Long merchantOrderId);
}
