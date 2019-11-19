package com.microservice.payment_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PaymentSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentSystemApplication.class, args);
    }

}
