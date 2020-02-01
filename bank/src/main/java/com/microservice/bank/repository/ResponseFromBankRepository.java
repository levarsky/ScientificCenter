package com.microservice.bank.repository;

import com.microservice.bank.model.ResponseFromBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseFromBankRepository extends JpaRepository<ResponseFromBank, Long> {


}
