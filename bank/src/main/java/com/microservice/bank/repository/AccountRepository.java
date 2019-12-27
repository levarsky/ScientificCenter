package com.microservice.bank.repository;

import com.microservice.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.merchant_id = ?1 and a.merchant_pass = ?2")
    Account getAccountWithMerchant(String merchant_id, String merchant_pass);
}
