package com.microservice.bank_service.service;

import com.microservice.bank_service.model.Client;
import com.microservice.bank_service.model.PaymentRequest;
import com.microservice.bank_service.model.Request;
import com.microservice.bank_service.model.Transaction;
import com.microservice.bank_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentService {


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public PaymentRequest pay(PaymentRequest paymentRequest){

        String url  = "https://localhost:8765";
        String urlBank  = "https://localhost:8768";

        System.out.println(paymentRequest.getClient().getClientId());

        Client client = paymentRequest.getClient();

        Transaction transaction = new Transaction();
        transaction.setMerchantOrderId(ThreadLocalRandom.current().nextInt(1000000, 2000000000));
        transaction.setAmount(paymentRequest.getAmount());
        transaction.setClientId(client.getClientId());
        transaction.setTimestamp(new Date());
        transactionRepository.save(transaction);

        Request request = new Request();
        request.setAmount(paymentRequest.getAmount());
        request.setMerchantId("FDSDGREGERGERGEG");
        request.setMerchantPassword("FDSAFSFASDFDASFDSAFDSSDF");
        request.setMerchantTimestamp(new Date());


        request.setErrorUrl(url+"/error/"+transaction.getMerchantOrderId());
        request.setFailedUrl(url+"/failed/"+transaction.getMerchantOrderId());
        request.setSuccessUrl(url+"/successful/"+transaction.getMerchantOrderId());


        HttpEntity<Request> requestEntity = new HttpEntity<Request>(request);

        ResponseEntity<String> exchange = restTemplate.exchange(urlBank+"/request", HttpMethod.POST, requestEntity,String.class);

        paymentRequest.setTransactionId(transaction.getMerchantOrderId().toString());

        return paymentRequest;

    }

    public void successful(Integer id){
        Transaction transaction = transactionRepository.findByMerchantOrderId(id);
        if(transaction == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "BRATE NEMA SA TIM ID-em");
        transaction.setSuccessful(true);
        transactionRepository.save(transaction);
        //REDIREKTOVATI
    }

    public void failed(Integer id){
        Transaction transaction = transactionRepository.findByMerchantOrderId(id);
        if(transaction == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "BRATE NEMA SA TIM ID-em");
        transaction.setSuccessful(false);
        transactionRepository.save(transaction);
        //REDIREKTOVATI
    }

    public void error(Integer id){
        Transaction transaction = transactionRepository.findByMerchantOrderId(id);
        if(transaction == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "BRATE NEMA SA TIM ID-em");
        transaction.setSuccessful(false);
        transactionRepository.save(transaction);
        //REDIREKTOVATI
    }

}
