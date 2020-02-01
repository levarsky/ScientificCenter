package com.microservice.bank.repository;

import com.microservice.bank.model.RequestFromBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestFromBankRepository extends JpaRepository<RequestFromBank, Long> {
}
