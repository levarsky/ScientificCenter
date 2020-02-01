package com.microservice.sellers_service.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.microservice.sellers_service.model.PaymentRequest;
import com.microservice.sellers_service.model.PaymentType;
import com.microservice.sellers_service.model.ProductDTO;
import com.microservice.sellers_service.repository.PaymentRequestRepository;

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

    public Object sendPaymentRequest(String token, Long id, String magazineName, String magazineType, String userGivenName, String userSurname, String userEmail){

        PaymentRequest paymentRequest = getRequest(token);
        PaymentType paymentType = paymentTypeService.getPaymentType(id);

        paymentRequest.setPaymentType(paymentType);

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest);
        ResponseEntity<PaymentRequest> exchange = null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("amount",paymentRequest.getAmount().toString());


        HttpHeaders httpHeadersPaypal = new HttpHeaders();
        httpHeadersPaypal.setContentType(MediaType.APPLICATION_JSON);
        
        ProductDTO productDto = new ProductDTO();
        productDto.setName(magazineName);
        productDto.setType(magazineType);
        productDto.setDescription("/");
        productDto.setSubscriberGivenName(userGivenName);
        productDto.setSubscriberSurname(userSurname);
        productDto.setSubscriberEmail(userEmail);
        productDto.setAmount(paymentRequest.getAmount());
        productDto.setClientId(paymentRequest.getClient().getClientId());
        
        String radi = "";
        if(!paymentType.getServiceName().equals("paypal-service")){
            exchange = restTemplate.exchange("https://"+paymentType.getServiceName()+"/pay", HttpMethod.POST, requestEntity, PaymentRequest.class);
        }else{
            radi = restTemplate.exchange("https://"+paymentType.getServiceName()+"/subscription/create", HttpMethod.POST, new HttpEntity<>(productDto, httpHeadersPaypal),String.class).getBody();
        }

        //PaymentRequest paymentRequest1 = this.bankPaymentServices.create(paymentRequest);
        
        //System.out.println(exchange.getBody());
        System.out.println(radi);

        PaymentRequest paymentRequestReceived = (PaymentRequest) exchange.getBody();

        paymentRequest.setTransactionId(paymentRequestReceived.getTransactionId());
        paymentRequestRepository.save(paymentRequest);

        return exchange.getBody();
    }


}
