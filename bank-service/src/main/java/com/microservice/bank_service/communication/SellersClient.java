package com.microservice.bank_service.communication;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sellers-service")
public interface SellersClient {

    @GetMapping(value = "/sellers/payment/status")
    String paymentStatus(@RequestParam String transactionId,@RequestParam String paymentStatus );

    @GetMapping(value = "/sellers/client/status")
    void clientSuccess(@RequestParam String clientId,@RequestParam String status,@RequestParam String paymentService);

}
