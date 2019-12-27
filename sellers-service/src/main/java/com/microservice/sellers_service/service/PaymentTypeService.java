package com.microservice.sellers_service.service;

import com.microservice.sellers_service.model.Client;
import com.microservice.sellers_service.model.PaymentType;
import com.microservice.sellers_service.model.Request;
import com.microservice.sellers_service.repository.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeService {

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ClientService clientService;

    public List<PaymentType> getPaymentTypes(String token){
        return requestService.getRequest(token).getClient().getPaymentTypes();
    }

    public List<PaymentType> getPaymentTypes(Request request){
        return clientService.findByClientId(request.getClient().getClientId()).getPaymentTypes();
    }


}
