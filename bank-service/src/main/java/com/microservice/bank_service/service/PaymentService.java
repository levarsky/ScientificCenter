package com.microservice.bank_service.service;

import com.microservice.bank_service.communication.SellersClient;
import com.microservice.bank_service.model.*;
import com.microservice.bank_service.repository.ClientRepository;
import com.microservice.bank_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentService {

    @Autowired
    private SellersClient sellersClient;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuth2RestOperations restTemplateBalanced;

    private String sellersService = "https://localhost:8679/sellers/payment/status";

    public Object pay(PaymentRequest paymentRequest){

        String url  = "https://localhost:8765/pay";
        String urlBank  = "https://localhost:8768";

        System.out.println(paymentRequest.getClient().getClientId());

        Client client = clientService.getClientById(paymentRequest.getClient().getClientId());

        System.out.println(paymentRequest.toString());

        Transaction transaction = new Transaction();
        transaction.setMerchantOrderId(ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L));
        transaction.setAmount(paymentRequest.getAmount());
        transaction.setClientId(client.getClientId());
        transaction.setTimestamp(new Date());
        transactionRepository.save(transaction);

        paymentRequest.setTransactionId(transaction.getMerchantOrderId().toString());

        Request request = new Request();
        request.setAmount(paymentRequest.getAmount());
        request.setMerchantId(client.getMerchantId());
        request.setMerchantPassword(client.getMerchantPassword());
        request.setMerchantOrderId(transaction.getMerchantOrderId());
        request.setMerchantTimestamp(new Date());


        request.setErrorUrl(url+"/error/"+transaction.getMerchantOrderId());
        request.setFailedUrl(url+"/failed/"+transaction.getMerchantOrderId());
        request.setSuccessUrl(url+"/successful/"+transaction.getMerchantOrderId());


        HttpEntity<Request> requestEntity = new HttpEntity<Request>(request);
        ResponseEntity<Object> exchange = restTemplate.exchange(urlBank+"/request", HttpMethod.POST, requestEntity, Object.class);

        paymentRequest.setTransactionId(transaction.getMerchantOrderId().toString());

        System.out.println(exchange.getBody());

        Map map =(Map) exchange.getBody();

        paymentRequest.setUrl(map.get("redirectUrl").toString());

        return paymentRequest;

    }

    public Object paymentStatus(Long id,String status){

        Transaction transaction = transactionRepository.findByMerchantOrderId(id);
        if(transaction == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "ID-em");
        if(status.equals("SUCCESSFUL")){
            transaction.setSuccessful(true);
        }else
            transaction.setSuccessful(false);

        transactionRepository.save(transaction);
        return Collections.singletonMap("redirectUrl",getUrl(sellersService,transaction.getMerchantOrderId().toString(),status));


    }

    public String getUrl(String url,String transactionId, String paymentStatus){

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("transactionId",transactionId)
                .queryParam("paymentStatus",paymentStatus);

        System.out.println(paymentStatus);



        String paymentUrl = builder.build().encode().toUriString();

        System.out.println(paymentUrl);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        sellersClient.paymentStatus(transactionId,paymentStatus);

        //ResponseEntity<String> exchange = restTemplateBalanced.exchange(paymentUrl, HttpMethod.GET, requestEntity, String.class);

        return sellersClient.paymentStatus(transactionId,paymentStatus);

    }

}
