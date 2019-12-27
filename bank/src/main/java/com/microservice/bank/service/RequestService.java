package com.microservice.bank.service;
import com.microservice.bank.model.Account;
import com.microservice.bank.model.Payment;
import com.microservice.bank.model.Request;
import com.microservice.bank.model.Response;
import com.microservice.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private AccountRepository accountRepository;

    public Response generateResponse(Request request){

        return new Response();
    }

    public void pay(Payment payment){
        List<Account> accounts = accountRepository.findAll();
        Account pom = null;
        Date date = new Date();
        for(Account a : accounts){
            if(a.getCardNumber() == payment.getPan() && a.getCvv() == payment.getSecurityCode() &&
                a.getCardHolderName().equals(payment.getCardHolderName()) && payment.getExpirationDate().after(date)){
                pom = a;
            }
        }
        if(pom == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"BRATE LOSE UNESENI PODACI");

        if(pom.getAmount() < 1000)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"BRATE NEMAS DOVOLJNO PARA");

        pom.setAmount(pom.getAmount()-1000);
    }
}
