package com.sep.pcc.repository;

import com.sep.pcc.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BankRepository  extends JpaRepository<Bank, Long> {
    @Query("SELECT b FROM Bank b WHERE b.pan = ?1")
    Bank findByPan(String pan);
}
