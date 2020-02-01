package com.microservice.paypal_service;

import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@Configuration
public class SSLConfigServiceBootstrapConfiguration {

	@Autowired
	ConfigClientProperties properties;

	@Bean
	public ConfigServicePropertySourceLocator configServicePropertySourceLocator() throws Exception {
		final char[] password = "password".toCharArray();
		final ClassPathResource resource = new ClassPathResource("paypal.p12");
		//final ClassPathResource resourceTrust = new ClassPathResource("eurekaTrust.jks");

		SSLContext sslContext = SSLContexts.custom()
				.loadKeyMaterial(resource.getFile(), password, password).build();
		//.loadTrustMaterial(resourceTrust.getFile(), password, new TrustSelfSignedStrategy()).build();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLContext(sslContext)
				.setSSLHostnameVerifier((s, sslSession) -> true)
				.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		ConfigServicePropertySourceLocator configServicePropertySourceLocator = new ConfigServicePropertySourceLocator(properties);
		configServicePropertySourceLocator.setRestTemplate(new RestTemplate(requestFactory));
		return configServicePropertySourceLocator;
	}
	
	@Bean
	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
		//System.setProperty("javax.net.ssl.keyStore", "/Users/mac/Documents/MASTER/SEP/Projekat/sc/paypal_service/src/main/resources/paypal.p12");
		System.setProperty("javax.net.ssl.keyStore", "C:\\Users\\Milica\\Documents\\FTN_Master\\SEP_UPP_UDD\\cloud branch\\ScientificCenter\\paypal_service\\src\\main\\resources\\paypal.p12");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		//System.setProperty("javax.net.ssl.trustStore", "src/main/resources/eurekaTrust.jks");
		//System.setProperty("javax.net.ssl.trustStorePassword", "password");
		EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
		builder.withClientName("paypal-service");
		builder.withSystemSSLConfiguration();
		builder.withMaxTotalConnections(10);
		builder.withMaxConnectionsPerHost(10);
		args.setEurekaJerseyClient(builder.build());
		return args;
	}

}
