package com.microservice.gateway;

import java.security.NoSuchAlgorithmException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public CloseableHttpClient httpClient() throws Throwable {

        Boolean followRedirects = DefaultClientConfigImpl.DEFAULT_FOLLOW_REDIRECTS;
        Integer connectTimeout = DefaultClientConfigImpl.DEFAULT_CONNECT_TIMEOUT;
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setRedirectsEnabled(followRedirects).build();
        return HttpClientBuilder.create()
                .setMaxConnTotal(DefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS)
                .setMaxConnPerRoute(DefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST)
                .disableContentCompression()
                .disableCookieManagement()
                .setDefaultRequestConfig(defaultRequestConfig)
                .useSystemProperties()
                .build();
    }

    @Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {


        System.out.println(System.getProperty("gateway.jks"));

        DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
        System.setProperty("javax.net.ssl.keyStore", "/Users/mac/Documents/MASTER/SEP/Projekat/sc/gateway/src/main/resources/gateway.p12");
        //System.setProperty("javax.net.ssl.keyStore", "src/main/resources/gateway.p12");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        System.setProperty("javax.net.ssl.trustStore", "/Users/mac/Documents/MASTER/SEP/Projekat/sc/gateway/src/main/resources/eurekaTrust.jks");
        //System.setProperty("javax.net.ssl.trustStore", "src/main/resources/eurekaTrust.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
        EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
        builder.withClientName("zuul-server");
        builder.withSystemSSLConfiguration();
        builder.withMaxTotalConnections(10);
        builder.withMaxConnectionsPerHost(10);
        args.setEurekaJerseyClient(builder.build());
        return args;
    }
}
