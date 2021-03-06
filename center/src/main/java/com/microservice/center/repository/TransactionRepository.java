package com.microservice.center.repository;

import com.microservice.center.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.token = ?1")
    Transaction findByToken(String token);

    List<Transaction> findAllByUsername(String username);
}
