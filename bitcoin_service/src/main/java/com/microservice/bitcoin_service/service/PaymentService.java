package com.microservice.bitcoin_service.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.bitcoin_service.dto.AuthDto;
import com.microservice.bitcoin_service.dto.FundsTransferDto;
import com.microservice.bitcoin_service.dto.TimeDto;

@Service
public class PaymentService {
	
	public static final String REST_API_URL = "https://api-public.sandbox.pro.coinbase.com";
	
	public HttpHeaders getHttpHeaders(AuthDto authDto) throws NoSuchAlgorithmException, InvalidKeyException {
		HttpHeaders timeHeaders = new HttpHeaders();
		timeHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
		HttpEntity timeEntity = new HttpEntity(null, timeHeaders);
		ResponseEntity<TimeDto> time = new RestTemplate().exchange(REST_API_URL + "/time", HttpMethod.GET, timeEntity, TimeDto.class);
		
		String secret = authDto.getSecret();
		String message = String.valueOf(time.getBody().getEpoch()) + "GET" + authDto.getPath();
		
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(Base64.decode(secret), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		
		String hash = Base64.toBase64String(sha256_HMAC.doFinal(message.getBytes()));
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("CB-ACCESS-KEY", authDto.getAccessKey());
		httpHeaders.add("CB-ACCESS-SIGN", hash);
		httpHeaders.add("CB-ACCESS-TIMESTAMP", String.valueOf(time.getBody().getEpoch()));
		httpHeaders.add("CB-ACCESS-PASSPHRASE", authDto.getPassphrase());
		httpHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
		
		return httpHeaders;
	}
	
	public List<GDAXAccount> getAccounts() throws NoSuchAlgorithmException, InvalidKeyException{
		/*HttpEntity request = new HttpEntity(null, httpHeaders);
		ResponseEntity<List> response = new RestTemplate().exchange(REST_API_URL + "/accounts", HttpMethod.GET, request, List.class);
		return (List<GDAXAccount>)response.getBody();*/
		return null;
	}
	
	public String getProfiles() throws NoSuchAlgorithmException, InvalidKeyException {
		HttpHeaders httpHeaders = this.getHttpHeaders(new AuthDto("/profiles", "X+SQgXmVK7lzuRwkUVRAE6ZRVRn3WOB6VHKDnFn+uG+kEwzHc+43Spie/hOxRmlQsRHkodwvltHgXkjtJKYBww==", "9a7e05bc686e6169862a9dfc5921e13c", "4pt7oez93pi"));
		HttpEntity request = new HttpEntity(null, httpHeaders);
		ResponseEntity<String> response = new RestTemplate().exchange(REST_API_URL + "/profiles", HttpMethod.GET, request, String.class);
		return response.getBody();
	}
}
