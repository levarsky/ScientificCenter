package com.sep.pcc.service;

import com.sep.pcc.model.Bank;
import com.sep.pcc.model.RequestFromBank;
import com.sep.pcc.model.ResponseFromBank;
import com.sep.pcc.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RequestService {

    @Autowired
    private OAuth2RestOperations restTemplateOauth;

    @Autowired
    private BankRepository bankRepository;

    public ResponseFromBank sendToBank(RequestFromBank requestFromBank){
        checkRequest(requestFromBank);
        Bank bank = bankRepository.findByPan(requestFromBank.getPAN());
        if(bank == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Brate mi tu banku ne podrzavamo!");

        HttpEntity<?> requestEntity = new HttpEntity<>(requestFromBank);

        ResponseEntity<ResponseFromBank> exchange = restTemplateOauth.exchange(bank.getApi(), HttpMethod.POST, requestEntity, ResponseFromBank.class);

        return exchange.getBody();
    }

    public void checkRequest(RequestFromBank requestFromBank){
        if(!isPanCardValid(requestFromBank.getPAN()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong pan number!");

        if(requestFromBank.getACQUIRER_TIMESTAMP().before(new Date()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong timestamp!");

        if(requestFromBank.getCARD_HOLDER_NAME().isEmpty())
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
}
