package com.microservice.sellers_service.service;

import com.microservice.sellers_service.model.PaymentType;
import com.microservice.sellers_service.repository.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PaymentTypeService {

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    public List<PaymentType> getPaymentTypes(){
        return null;
    }
}
