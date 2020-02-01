package com.sep.pcc.service;

import com.sep.pcc.model.Bank;
import com.sep.pcc.model.RequestFromBank;
import com.sep.pcc.model.RequestTest;
import com.sep.pcc.model.ResponseFromBank;
import com.sep.pcc.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RequestService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BankRepository bankRepository;

    public ResponseFromBank sendToBank(RequestFromBank requestFromBank){
        System.out.println("USAOOOOO");

        System.out.println(requestFromBank.getPan());
        //checkRequest(requestFromBank);

        System.out.println(requestFromBank.getPan().substring(0,6));

        Bank bank = bankRepository.findByCode(requestFromBank.getPan().substring(0,6));

        System.out.println(bank.getApi());

        if(bank == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Brate mi tu banku ne podrzavamo!");

        HttpEntity<?> requestEntity = new HttpEntity<>(requestFromBank);



        ResponseEntity<ResponseFromBank> exchange = restTemplate.exchange(bank.getApi(), HttpMethod.POST, requestEntity, ResponseFromBank.class);

        return exchange.getBody();
    }

    public void checkRequest(RequestFromBank requestFromBank){
        if(!isPanCardValid(requestFromBank.getPan()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong pan number!");

        if(requestFromBank.getAcquirerTimestamp().before(new Date()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong timestamp!");

        if(requestFromBank.getCardHolderName().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong name!");

    }

    public static boolean isPanCardValid(String pan_number) {
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(pan_number);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public Object test() {

        return "POGODIO";
    }
}
