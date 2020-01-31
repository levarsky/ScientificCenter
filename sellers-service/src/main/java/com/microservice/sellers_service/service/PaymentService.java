package com.microservice.sellers_service.service;

import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.model.PaymentRequest;
import com.microservice.sellers_service.model.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PaymentService {

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Autowired
    private OAuth2RestOperations restTemplateNotBalanced;

    public String paymentStatus(String transactionId, String paymentStatus) {

        System.out.println("Dosao dovdee");

        PaymentRequest paymentRequest = paymentRequestService.getRequestByTransactionId(transactionId);
        Client client = paymentRequest.getClient();

        paymentRequest.setStatus(paymentStatus.toString());
        paymentRequestService.saveRequest(paymentRequest);

        if(paymentStatus.equals("ERROR"))
            return uriBuilder(client.getErrorUrl(),paymentRequest.getToken());
        if(paymentStatus.equals("FAILED"))
            return uriBuilder(client.getFailedUrl(),paymentRequest.getToken());

        //SUCCESS
        return uriBuilder(client.getSuccessUrl(),paymentRequest.getToken());
    }

    public String uriBuilder(String url,String requestId){
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("requestId",requestId);

        String paymentUrl = builder.build().encode().toUriString();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<String> exchange = restTemplateNotBalanced.exchange(paymentUrl, HttpMethod.POST, requestEntity, String.class);

        return exchange.getBody();
    }

}
