package com.microservice.bank.repository;

import com.microservice.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    //Account getAccountWithMerchant(String merchant_id, String merchant_pass);

    Account findByMerchantIdAndMerchantPassword(String merchantId,String merchantPassword);
    Optional<Account> findByCardNumberAndCvvAndCardHolderName(String cardNumber,String cvv,String cardHolderName);
}
