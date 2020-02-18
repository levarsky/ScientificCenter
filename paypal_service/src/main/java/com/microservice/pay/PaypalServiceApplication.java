package com.microservice.pay;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@SpringBootApplication
@EnableDiscoveryClient
public class PaypalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaypalServiceApplication.class, args);
	}

	//@Value("${paypal.client.app}")
	@Value("AQ-JUhmxs9ZQU619meknUmn6hbJoafvclUDuzcgK51w3js8VHvFK_eQOWs_i-YiBxz_nwbztqj6w_sfX")
	private String clientId;
	//@Value("${paypal.client.secret}")
	@Value("EKa_NIeCT-zhIbw_ZgQm8z-zEoQJUzPGD8AncNR1wuo9jzNKrt5sQBD58YglYNalV7cJKo3vCaCLoKEd")
	private String clientSecret;
	//@Value("${paypal.mode}")
	@Value("sandbox")
	private String mode;

	@Bean
	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
		System.setProperty("javax.net.ssl.keyStore", "C:/Users/PC/IdeaProjects/ScientificCenter/paypal_service/src/main/resources/bank.p12");
		//System.setProperty("javax.net.ssl.keyStore", "C:\\Users\\Milica\\Documents\\FTN_Master\\SEP_UPP_UDD\\cloud branch\\ScientificCenter\\sellers-service\\src\\main\\resources\\bank.p12");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		System.setProperty("javax.net.ssl.trustStore", "C:/Users/PC/IdeaProjects/ScientificCenter/paypal_service/src/main/resources/eurekaTrust.jks");
		//System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\Milica\\Documents\\FTN_Master\\SEP_UPP_UDD\\cloud branch\\ScientificCenter\\sellers-service\\src\\main\\resources\\configTrust.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "password");
		EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
		builder.withClientName("paypal-service");
		builder.withSystemSSLConfiguration();
		builder.withMaxTotalConnections(10);
		builder.withMaxConnectionsPerHost(10);
		args.setEurekaJerseyClient(builder.build());
		return args;
	}

	@Bean
	public Map<String, String> paypalSdkConfig(){
		Map<String, String> sdkConfig = new HashMap<>();
		sdkConfig.put("mode", mode);
		return sdkConfig;
	}

	@Bean
	public OAuthTokenCredential authTokenCredential(){
		return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
	}

	@Bean
	public APIContext apiContext() throws PayPalRESTException{
		APIContext apiContext = new APIContext(authTokenCredential().getAccessToken());
		apiContext.setConfigurationMap(paypalSdkConfig());
		return apiContext;
	}



}
