package com.microservice.bank_service;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class BankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankServiceApplication.class, args);
    }


    @Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
        DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
        //System.setProperty("javax.net.ssl.keyStore", "C:/Users/PC/IdeaProjects/ScientificCenter/bank-service/src/main/resources/bank.p12");
        System.setProperty("javax.net.ssl.keyStore", "C:/Users/Veljko/IdeaProjects/ScientificCenter/bank-service/src/main/resources/bank.p12");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        //System.setProperty("javax.net.ssl.trustStore", "C:/Users/PC/IdeaProjects/ScientificCenter/bank-service/src/main/resources/eurekaTrust.jks");
        System.setProperty("javax.net.ssl.trustStore", "C:/Users/Veljko/IdeaProjects/ScientificCenter/bank-service/src/main/resources/eurekaTrust.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
        EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
        builder.withClientName("bank-service");
        builder.withSystemSSLConfiguration();
        builder.withMaxTotalConnections(10);
        builder.withMaxConnectionsPerHost(10);
        args.setEurekaJerseyClient(builder.build());
        return args;
    }
}
