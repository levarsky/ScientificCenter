package com.microservice.bank.repository;

import com.microservice.bank.model.Account;
import com.microservice.bank.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r WHERE r.payment_id = ?1")
    Request getRequestWithPaymentId(String payment_id);
}
