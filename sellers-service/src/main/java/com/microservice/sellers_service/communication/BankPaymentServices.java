package com.microservice.sellers_service.communication;

import com.microservice.sellers_service.model.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "bank-service")
public interface BankPaymentServices {

    @PostMapping(value = "/pay")
    PaymentRequest create(@RequestBody PaymentRequest paymentRequest);
}
