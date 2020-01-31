package com.microservice.bank.service;

import com.microservice.bank.model.Account;
import com.microservice.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

    public Account getAccount(String cardNumber, String cvv, String name, Date date){


        System.out.println(cardNumber+cvv+name+date);

        Optional<Account> optionalAccount = accountRepository.findByCardNumberAndCvvAndCardHolderName(cardNumber,cvv,name);

        if (optionalAccount.isPresent()){
            Account account = optionalAccount.get();

            Calendar accountCall = getCalendar(account.getExpirationDate());
            Calendar calendarSent = getCalendar(date);


            if (accountCall.get(Calendar.MONTH)==calendarSent.get(Calendar.MONTH)){
                if (accountCall.get(Calendar.YEAR)==calendarSent.get(Calendar.YEAR))
                    return account;
            }

        }

       return null;
    }

    public Calendar getCalendar(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;

    }

}
