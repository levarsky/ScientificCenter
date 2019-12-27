package com.microservice.sellers_service.service;

import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitiateService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PaymentTypeService paymentTypeService;





}
