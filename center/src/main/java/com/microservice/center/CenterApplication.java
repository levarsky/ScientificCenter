package com.microservice.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CenterApplication.class, args);
    }

}
