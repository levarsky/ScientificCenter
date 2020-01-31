package com.microservice.sellers_service.service;

import com.microservice.sellers_service.communication.BankPaymentServices;
import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.model.PaymentRequest;
import com.microservice.sellers_service.model.PaymentType;
import com.microservice.sellers_service.repository.PaymentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentRequestService {

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private OAuth2RestOperations restTemplate;
    @Autowired
    private AuthService authService;

    public PaymentRequest saveRequest(PaymentRequest paymentRequest){
        return paymentRequestRepository.save(paymentRequest);
    }

    public PaymentRequest createRequest(double amount){

        String token = UUID.randomUUID().toString();

        PaymentRequest request = new PaymentRequest();

        String clientId = authService.getAuth().getPrincipal().toString();

        request.setAmount(amount);
        request.setClient(clientService.findByClientId(clientId));
        request.setToken(token);


        return paymentRequestRepository.save(request);
    }

    public PaymentRequest getRequest(String token){
        return this.paymentRequestRepository.findByToken(token).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token does not exist"));

    }

    public PaymentRequest getRequestByTransactionId(String transactionId){
        return this.paymentRequestRepository.findByTransactionId(transactionId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction does not exist"));

    }

    public Object sendPaymentRequest(String token, Long id){

        PaymentRequest paymentRequest = getRequest(token);
        PaymentType paymentType = paymentTypeService.getPaymentType(id);

        paymentRequest.setPaymentType(paymentType);

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest);
        ResponseEntity<PaymentRequest> exchange = null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("amount",paymentRequest.getAmount().toString());




        if(paymentType.getServiceName()!="paypal-service"){
            exchange = restTemplate.exchange("https://"+paymentType.getServiceName()+"/pay", HttpMethod.POST, requestEntity, PaymentRequest.class);
        }else{
            //exchange = restTemplate.exchange("https://"+paymentType.getServiceName()+"/pay?amount="+paymentRequest.getAmount(), HttpMethod.POST, new HttpEntity<>(httpHeaders),PaymentRequest.class);
        }

        //PaymentRequest paymentRequest1 = this.bankPaymentServices.create(paymentRequest);

        System.out.println(exchange.getBody());

        PaymentRequest paymentRequestReceived = (PaymentRequest) exchange.getBody();

        paymentRequest.setTransactionId(paymentRequestReceived.getTransactionId());
        paymentRequestRepository.save(paymentRequest);

        return exchange.getBody();
    }


}
