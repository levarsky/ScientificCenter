package com.microservice.bank.service;

import com.microservice.bank.model.Account;
import com.microservice.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> getAccount(String cardNumber, String cvv, String name, Date date){
       Optional<Account> optionalAccount = accountRepository.findByCardNumberAndCvvAndCardHolderNameAndExpirationDateIsGreaterThanEqual(cardNumber,cvv,name,date);
       return optionalAccount;
    }

}
