package com.microservice.payment_system_sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class PaymentSystemScApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentSystemScApplication.class, args);
    }

}
