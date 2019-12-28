package com.microservice.sellers_service.service;

import com.microservice.sellers_service.communication.BankPaymentServices;
import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.model.PaymentRequest;
import com.microservice.sellers_service.model.PaymentType;
import com.microservice.sellers_service.repository.PaymentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

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
    private RestTemplate restTemplate;
    @Autowired
    private BankPaymentServices bankPaymentServices;

    public PaymentRequest createRequest(String clientId,double amount){

        String token = UUID.randomUUID().toString();

        PaymentRequest request = new PaymentRequest();

        request.setAmount(amount);
        request.setClient(clientService.findByClientId(clientId));
        request.setToken(token);

        return paymentRequestRepository.save(request);
    }

    public PaymentRequest getRequest(String token){
        return this.paymentRequestRepository.findByToken(token).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token does not exist"));

    }

    public PaymentRequest sendPaymentRequest(String token, Long id){

        PaymentRequest paymentRequest = getRequest(token);
        PaymentType paymentType = paymentTypeService.getPaymentType(id);

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest);

        ResponseEntity<PaymentRequest> exchange = restTemplate.exchange("https://"+paymentType.getServiceName()+"/pay", HttpMethod.POST, requestEntity,PaymentRequest.class);

        PaymentRequest paymentRequest1 = this.bankPaymentServices.create(paymentRequest);

        return paymentRequest1;
    }

}
