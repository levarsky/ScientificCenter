package com.microservice.bank.repository;

import com.microservice.bank.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionTypeRepository extends JpaRepository<TransactionType,Long> {
        Optional<TransactionType> findByName(String name);
}
