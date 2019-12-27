package com.microservice.bank.service;
import com.microservice.bank.model.Account;
import com.microservice.bank.model.Payment;
import com.microservice.bank.model.Request;
import com.microservice.bank.model.Response;
import com.microservice.bank.repository.AccountRepository;
import com.microservice.bank.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountNotFoundException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RequestService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RequestRepository requestRepository;

    public void generateResponse(Request request){
        Account account = accountRepository.getAccountWithMerchant(request.getMERCHANT_ID(),request.getMERCHANT_PASSWORD());
        if(account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"BRATE MERCHANT TI NE VALJA");

        if(request.getAMOUNT() == null || request.getMERCHANT_ORDER_ID() == null  ||
                request.getMERCHANT_TIMESTAMP() == null || request.getSUCCESS_URL() == null ||
                request.getFAILED_URL() == null || request.getERROR_URL() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BRATE NE SME NISTA DA BUDE NULL");

        request.setPAYMENT_ID(generateRandomPaymentId());
        requestRepository.save(request);

        Response response = new Response();
        response.setPAYMENT_ID(request.getPAYMENT_ID());
        response.setPAYMENT_URL("http://localhost:8769/pay/");
        //OVDE REDIREKTUJ NA FORMU ZA PLACANJE
    }

    public void pay(Payment payment, String id){
        List<Account> accounts = accountRepository.findAll();
        Account pom = null;
        Date date = new Date();
        for(Account a : accounts){
            if(a.getCardNumber().equals(payment.getPan()) && a.getCvv().equals(payment.getSecurityCode()) &&
                a.getCardHolderName().equals(payment.getCardHolderName()) && payment.getExpirationDate().after(date)){
                pom = a;
            }
        }

        if(pom == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"BRATE LOSE UNESENI PODACI");

        Request request = requestRepository.getRequestWithPaymentId(id);
        if(request == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"BRATE NEMA TE TRANSAKCIJE");

        if(pom.getAmount() < request.getAMOUNT())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"BRATE NEMAS DOVOLJNO PARA");

        Account prodavac = accountRepository.getAccountWithMerchant(request.getMERCHANT_ID(),request.getMERCHANT_PASSWORD());

        pom.setAmount(pom.getAmount() - request.getAMOUNT());
        prodavac.setAmount(prodavac.getAmount() + request.getAMOUNT());

        accountRepository.save(pom);
    }

    private String generateRandomPaymentId() {
        byte[] array = new byte[30]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }
}
