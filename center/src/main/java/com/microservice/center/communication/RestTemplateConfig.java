package com.microservice.center.communication;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class RestTemplateConfig {

    @Value("${sellers.security.clientId}")
    private String clientId;

    @Value("${sellers.security.clientSecret}")
    private String clientSecret;

    @Value("${sellers.security.grantType}")
    private String grantType;

    @Value("${sellers.security.scope}")
    private String scope;

    @Value("${sellers.accessTokenUri}")
    private String accessTokenUri;

//    @Bean
//    public RestTemplate
//    restTemplate() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException {
//
//        return new RestTemplate(getRequestFactory());
//    }

    @Bean
    public OAuth2RestOperations restTemplateOauth() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException {

        DefaultOAuth2ClientContext defaultOAuth2ClientContext =
                new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());

        OAuth2RestTemplate oAuth2RestTemplate =
                new OAuth2RestTemplate(clientDetails(),defaultOAuth2ClientContext);

        oAuth2RestTemplate.setRequestFactory(getRequestFactory());
        oAuth2RestTemplate.setAccessTokenProvider(clientAccessTokenProvider());

        return oAuth2RestTemplate;

    }

    @Bean
    public AccessTokenProvider clientAccessTokenProvider() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException {
        ClientCredentialsAccessTokenProvider accessTokenProvider = new ClientCredentialsAccessTokenProvider();
        accessTokenProvider.setRequestFactory(getRequestFactory());
        return accessTokenProvider;
    }


    @Bean
    public OAuth2ProtectedResourceDetails clientDetails(){

        ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();

        clientCredentialsResourceDetails.setAccessTokenUri(accessTokenUri);
        clientCredentialsResourceDetails.setClientId(clientId);
        clientCredentialsResourceDetails.setClientSecret(clientSecret);
        clientCredentialsResourceDetails.setGrantType(grantType);
        clientCredentialsResourceDetails.setClientAuthenticationScheme(AuthenticationScheme.form);
        clientCredentialsResourceDetails.setScope(new ArrayList<>(Arrays.asList("server")));

        return clientCredentialsResourceDetails;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory getRequestFactory() throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadKeyMaterial(ResourceUtils.getFile("classpath:center.p12"), "password".toCharArray(), "password".toCharArray())
                .loadTrustMaterial(ResourceUtils.getFile("classpath:configTrust.jks"), "password".toCharArray())
                .build();

        HttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(client);

        return requestFactory;
    }


}
