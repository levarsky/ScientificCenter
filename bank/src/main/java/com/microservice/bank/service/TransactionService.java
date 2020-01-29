package com.microservice.bank.service;

import com.microservice.bank.model.Transaction;
import com.microservice.bank.model.TransactionType;
import com.microservice.bank.repository.TransactionRepository;
import com.microservice.bank.repository.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    public Transaction saveTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public TransactionType getTransactionType(String name){
        return transactionTypeRepository.findByName(name).orElseThrow(() -> new RuntimeException("Name can't be found!"));
    }

    public Transaction saveNew(Transaction transaction,String name){

        long transactionId = ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L);

        TransactionType transactionType = getTransactionType(name);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionId(transactionId);

       return saveTransaction(transaction);
    }



}
