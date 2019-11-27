package com.microservice.payment_system.communication;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate
    restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate
    restTemplateNoLoad(){ return new RestTemplate(); }


}
