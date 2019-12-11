package com.microservice.gatewaycloud;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import io.netty.handler.ssl.SslContextBuilder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.function.Function;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayCloudApplication {

    @Value("${server.ssl.trust-store}")
    String trustStorePath;
    @Value("${server.ssl.trust-store-password}")
    String trustStorePass;
    @Value("${server.ssl.key-store}")
    String keyStorePath;
    @Value("${server.ssl.key-store-password}")
    String keyStorePass;

    public static void main(String[] args) {
        SpringApplication.run(GatewayCloudApplication.class, args);

    }


    @Bean
    public WebClient webClient() {
        // Interact with client to initialize connection provider.


        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(new FileInputStream(ResourceUtils.getFile(keyStorePath)), keyStorePass.toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, keyStorePass.toCharArray());

            KeyStore trustStore = KeyStore.getInstance("PKCS12");
            trustStore.load(new FileInputStream(ResourceUtils.getFile(trustStorePath)), trustStorePass.toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(trustStore);

            SslContextBuilder sslContextBuilder = SslContextBuilder.forClient().keyManager(keyManagerFactory)
                    .trustManager(trustManagerFactory);

            // provide a specific SSL config such as client certificates.
            HttpClient otherClient = HttpClient.create().secure(builder -> {
                builder.sslContext(sslContextBuilder);
            });


            WebClient webClient = WebClient.builder()
                    .baseUrl("https://localhost:8088")
                    .clientConnector(new ReactorClientHttpConnector(otherClient))
                    .build();

            return webClient;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }


//	@Bean
//	public CloseableHttpClient httpClient() throws Throwable {
//
//		Boolean followRedirects = DefaultClientConfigImpl.DEFAULT_FOLLOW_REDIRECTS;
//		Integer connectTimeout = DefaultClientConfigImpl.DEFAULT_CONNECT_TIMEOUT;
//		RequestConfig defaultRequestConfig = RequestConfig.custom()
//				.setConnectTimeout(connectTimeout)
//				.setRedirectsEnabled(followRedirects).build();
//		return HttpClientBuilder.create()
//				.setMaxConnTotal(DefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS)
//				.setMaxConnPerRoute(DefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST)
//				.disableContentCompression()
//				.disableCookieManagement()
//				.setDefaultRequestConfig(defaultRequestConfig)
//				.useSystemProperties()
//				.build();
//	}

//	@Bean
//	public CloseableHttpClient httpClient() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException, UnrecoverableKeyException {
//
//		final ClassPathResource resource = new ClassPathResource("gateway.p12");
//		final ClassPathResource resourceTrust = new ClassPathResource("gatewayTrust.jks");
//
//
//		SSLContext sslcontext = SSLContexts.custom()
//				.loadKeyMaterial(resource.getFile(),"password".toCharArray(),"password".toCharArray())
//				.loadTrustMaterial(resourceTrust.getFile(), "password".toCharArray(), new TrustSelfSignedStrategy())
//				.build();
//		// Allow TLSv1 protocol only
//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
//				SSLConnectionSocketFactory.getDefaultHostnameVerifier());
//		CloseableHttpClient httpclient = HttpClients.custom().setSSLContext(sslcontext)
//				.setSSLHostnameVerifier((s, sslSession) -> true)
//				.build();
//
//		return httpclient;
//	}

    @Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
        DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
        System.setProperty("javax.net.ssl.keyStore", "/Users/mac/Documents/MASTER/SEP/Projekat/sc/gateway-cloud/src/main/resources/gateway.p12");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        System.setProperty("javax.net.ssl.trustStore", "/Users/mac/Documents/MASTER/SEP/Projekat/sc/gateway-cloud/src/main/resources/eurekaTrust.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");
        EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
        builder.withClientName("gateway-cloud");
        builder.withSystemSSLConfiguration();
        builder.withMaxTotalConnections(10);
        builder.withMaxConnectionsPerHost(10);
        args.setEurekaJerseyClient(builder.build());
        return args;
    }



}
