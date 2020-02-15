package com.microservice.sellers_service.service;

import com.microservice.sellers_service.model.PaymentType;
import com.microservice.sellers_service.model.PaymentRequest;
import com.microservice.sellers_service.repository.PaymentTypeRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentTypeService {

    @Autowired
    private AuthService authService;

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Autowired
    private ClientService clientService;

    public PaymentType getByServiceName(String name){
        return paymentTypeRepository.findByServiceName(name);
    }


    public List<PaymentType> getPaymentTypes(String token){
        return paymentRequestService.getRequest(token).getClient().getPaymentTypes();
    }

    public List<PaymentType> getPaymentTypes(PaymentRequest request){
        return clientService.findByClientId(request.getClient().getClientId()).getPaymentTypes();
    }

    public PaymentType getPaymentType(Long id){
        return paymentTypeRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment type does not exist"));
    }

    public List<PaymentType> getPaymentByClient(){
        return clientService.getClient().getPaymentTypes();
    }

    public List<PaymentType> getNotAdded(){
        List<PaymentType> clientsPaymentTypes = getPaymentByClient();
        List<PaymentType> all = allPaymentTypes();

        List<PaymentType> tempPaymentTypes = new ArrayList<>();

        for(PaymentType p:all){
            if(!clientsPaymentTypes.contains(p))
                    tempPaymentTypes.add(p);
        }

        return tempPaymentTypes;
    }

    public List<PaymentType> allPaymentTypes(){
        return paymentTypeRepository.findAll();
    }



}
