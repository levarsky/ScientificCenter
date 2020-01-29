package com.microservice.bank_service.service;

import com.microservice.bank_service.model.*;
import com.microservice.bank_service.repository.ClientRepository;
import com.microservice.bank_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestTemplate restTemplateBalanced;

    private String sellersService = "https://sellers-service/sellers/payment/status";

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

    public Object successful(Long id){
        System.out.println(id);
        Transaction transaction = transactionRepository.findByMerchantOrderId(id);
        if(transaction == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "ID-em");
        transaction.setSuccessful(true);
        transactionRepository.save(transaction);
        System.out.println("SAVEOVAO");

        return Collections.singletonMap("redirectUrl",getUrl(sellersService,transaction.getMerchantOrderId().toString(),PaymentStatus.SUCCESSFUL));

    }

    public Object failed(Long id){
        Transaction transaction = transactionRepository.findByMerchantOrderId(id);
        if(transaction == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "ID-em");
        transaction.setSuccessful(false);
        transactionRepository.save(transaction);
        return Collections.singletonMap("redirectUrl",getUrl(sellersService,transaction.getMerchantOrderId().toString(),PaymentStatus.FAILED));

    }

    public Object error(Long id){
        Transaction transaction = transactionRepository.findByMerchantOrderId(id);
        if(transaction == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "ID-em");
        transaction.setSuccessful(false);
        transactionRepository.save(transaction);
        return Collections.singletonMap("redirectUrl",getUrl(sellersService,transaction.getMerchantOrderId().toString(),PaymentStatus.ERROR));

    }

    public String getUrl(String url,String transactionId, PaymentStatus paymentStatus){

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("transactionId",transactionId)
                .queryParam("paymentStatus",paymentStatus);

        String paymentUrl = builder.build().encode().toUriString();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<String> exchange = restTemplateBalanced.exchange(paymentUrl, HttpMethod.GET, requestEntity, String.class);

        return exchange.getBody();

    }

}
