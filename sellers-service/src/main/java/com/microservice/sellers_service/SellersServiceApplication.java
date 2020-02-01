package com.microservice.sellers_service;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class SellersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellersServiceApplication.class, args);
    }

    @Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
        DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
        //System.setProperty("javax.net.ssl.keyStore", "C:/Users/PC/IdeaProjects/ScientificCenter/sellers-service/src/main/resources/bank.p12");
        System.setProperty("javax.net.ssl.keyStore", "C:\\Users\\Milica\\Documents\\FTN_Master\\SEP_UPP_UDD\\cloud branch\\ScientificCenter\\sellers-service\\src\\main\\resources\\bank.p12");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        //System.setProperty("javax.net.ssl.trustStore", "C:/Users/PC/IdeaProjects/ScientificCenter/sellers-service/src/main/resources/configTrust.jks");
        System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\Milica\\Documents\\FTN_Master\\SEP_UPP_UDD\\cloud branch\\ScientificCenter\\sellers-service\\src\\main\\resources\\configTrust.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
        EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
        builder.withClientName("sellers-service");
        builder.withSystemSSLConfiguration();
        builder.withMaxTotalConnections(10);
        builder.withMaxConnectionsPerHost(10);
        args.setEurekaJerseyClient(builder.build());
        return args;
    }


}
