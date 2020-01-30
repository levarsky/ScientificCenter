package com.microservice.bank.repository;


import com.microservice.bank.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Optional<Merchant> findByMerchantIdAndMerchantPassword(String merchantId,String merchantPassword);

    boolean existsByAccount_Id(Long id);
}
