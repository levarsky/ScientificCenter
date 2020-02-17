package com.microservice.authservice;

import com.microservice.authservice.service.ClientService;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);

    }

    @Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
        DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
        //System.setProperty("javax.net.ssl.keyStore", "C:/Users/Veljko/IdeaProjects/ScientificCenter/auth-service/src/main/resources/auth.p12");
        System.setProperty("javax.net.ssl.keyStore", "C:\\Users\\Milica\\Documents\\FTN_Master\\SEP_UPP_UDD\\cloud branch\\ScientificCenter\\auth-service\\src\\main\\resources\\auth.p12");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        //System.setProperty("javax.net.ssl.trustStore", "C:/Users/Veljko/IdeaProjects/ScientificCenter/auth-service/src/main/resources/configTrust.jks");
        System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\Milica\\Documents\\FTN_Master\\SEP_UPP_UDD\\cloud branch\\ScientificCenter\\auth-service\\src\\main\\resources\\configTrust.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
        EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
        builder.withClientName("auth-service");
        builder.withSystemSSLConfiguration();
        builder.withMaxTotalConnections(10);
        builder.withMaxConnectionsPerHost(10);
        args.setEurekaJerseyClient(builder.build());
        return args;
    }

}
