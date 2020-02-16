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

import com.microservice.bitcoin_service.dto.FundsTransferDto;
import com.microservice.bitcoin_service.dto.TimeDto;

@Service
public class AccountService {
	
	public static final String REST_API_URL = "https://api-public.sandbox.pro.coinbase.com";
	
	public List<GDAXAccount> getAccounts() throws NoSuchAlgorithmException, InvalidKeyException{
		HttpHeaders timeHeaders = new HttpHeaders();
		timeHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
		HttpEntity timeEntity = new HttpEntity(null, timeHeaders);
		ResponseEntity<TimeDto> time = new RestTemplate().exchange(REST_API_URL + "/time", HttpMethod.GET, timeEntity, TimeDto.class);
		
		String secret = "nrgXkS7Rq+SBePeLpmOX31LlPKtxV7S4Dtsz/qg1+DQLLM7lQCW+T1mR4JmQFYSN7818vpSbd+j+T/WjJR4ipA==";
		String message = String.valueOf(time.getBody().getEpoch()) + "GET" + "/accounts";
		
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(Base64.decode(secret), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		
		String hash = Base64.toBase64String(sha256_HMAC.doFinal(message.getBytes()));
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("CB-ACCESS-KEY", "af72560ced75fc38452e82376bb5bd68");
		httpHeaders.add("CB-ACCESS-SIGN", hash);
		httpHeaders.add("CB-ACCESS-TIMESTAMP", String.valueOf(time.getBody().getEpoch()));
		httpHeaders.add("CB-ACCESS-PASSPHRASE", "pakgqh4ag1a");
		httpHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
		HttpEntity request = new HttpEntity(null, httpHeaders);
		ResponseEntity<List> response = new RestTemplate().exchange(REST_API_URL + "/accounts", HttpMethod.GET, request, List.class);
		return (List<GDAXAccount>)response.getBody();
	}
	
	public void transferFunds(FundsTransferDto dto) throws NoSuchAlgorithmException, InvalidKeyException {
		HttpHeaders timeHeaders = new HttpHeaders();
		timeHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
		HttpEntity timeEntity = new HttpEntity(null, timeHeaders);
		ResponseEntity<TimeDto> time = new RestTemplate().exchange(REST_API_URL + "/time", HttpMethod.GET, timeEntity, TimeDto.class);
		
		String secret = "nrgXkS7Rq+SBePeLpmOX31LlPKtxV7S4Dtsz/qg1+DQLLM7lQCW+T1mR4JmQFYSN7818vpSbd+j+T/WjJR4ipA==";
		String message = String.valueOf(time.getBody().getEpoch()) + "POST" + "/profiles/transfer";
		
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(Base64.decode(secret), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		
		String hash = Base64.toBase64String(sha256_HMAC.doFinal(message.getBytes()));
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("CB-ACCESS-KEY", "af72560ced75fc38452e82376bb5bd68");
		httpHeaders.add("CB-ACCESS-SIGN", hash);
		httpHeaders.add("CB-ACCESS-TIMESTAMP", String.valueOf(time.getBody().getEpoch()));
		httpHeaders.add("CB-ACCESS-PASSPHRASE", "pakgqh4ag1a");
		httpHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
		HttpEntity request = new HttpEntity(dto, httpHeaders);
		ResponseEntity<Void> response = new RestTemplate().exchange(REST_API_URL + "/profiles/transfer", HttpMethod.POST, request, Void.class);
	}
}
