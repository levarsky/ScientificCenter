package com.microservice.bank_service.service;

import com.microservice.bank_service.model.Client;
import com.microservice.bank_service.model.Request;
import com.microservice.bank_service.model.Transaction;
import com.microservice.bank_service.repository.ClientRepository;
import com.microservice.bank_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void pay(Double amount){
        Client client = clientRepository.getClientById("UNESI ID");
        Request request = new Request();
        request.setAMOUNT(amount);
        request.setMERCHANT_ID(client.getMERCHANT_ID());
        request.setMERCHANT_PASSWORD(client.getMERCHANT_PASSWORD());
        request.setMERCHANT_TIMESTAMP(new Date());
        request.setERROR_URL("URL DO ENDPOINTA IZ OVOG SERVISA ZA ERROR");
        request.setFAILED_URL("");
        request.setSUCCESS_URL("");


        Transaction transaction = new Transaction();
        transaction.setMERCHANT_ORDER_ID(ThreadLocalRandom.current().nextInt(1000000, 2000000000));
        transaction.setAmount(amount);
        transaction.setClientId(client.getClientId());
        transaction.setTimestamp(new Date());
        transactionRepository.save(transaction);

        //OVDE POSLATI BANCI
    }

    public void successful(Integer id){
        Transaction transaction = transactionRepository.getTransactionByMerchantOrderId(id);
        if(transaction == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "BRATE NEMA SA TIM ID-em");
        transaction.setSuccessful(true);
        transactionRepository.save(transaction);
        //REDIREKTOVATI
    }

    public void failed(Integer id){
        Transaction transaction = transactionRepository.getTransactionByMerchantOrderId(id);
        if(transaction == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "BRATE NEMA SA TIM ID-em");
        transaction.setSuccessful(false);
        transactionRepository.save(transaction);
        //REDIREKTOVATI
    }

    public void error(Integer id){
        Transaction transaction = transactionRepository.getTransactionByMerchantOrderId(id);
        if(transaction == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "BRATE NEMA SA TIM ID-em");
        transaction.setSuccessful(false);
        transactionRepository.save(transaction);
        //REDIREKTOVATI
    }

}
