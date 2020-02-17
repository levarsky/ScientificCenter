package com.microservice.bitcoin_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microservice.bitcoin_service.dto.PaymentDto;
import com.microservice.bitcoin_service.dto.ProductDto;
import com.microservice.bitcoin_service.dto.SellerDataDto;

@Service
public class PaymentService {
	
	public static final String REST_API_URL = "https://api-sandbox.coingate.com/v2";
	
	@Autowired
	ClientService clientService;
	
	/*public HttpHeaders getHttpHeaders(AuthDto authDto) throws NoSuchAlgorithmException, InvalidKeyException {
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
	}*/
	
	public SellerDataDto pay(SellerDataDto sellerData) {
		String token = clientService.findByClientId(sellerData.getClientId()).getBitcoinToken();
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + token);
		header.setContentType(MediaType.APPLICATION_JSON);
		
		String description = "";
		for(ProductDto product : sellerData.getProducts()) {
			if(description.equals("")) {
				description += "Magazines in cart: " + product.getName();
			}
			else {
				description += ("," + product.getName()); 
			}
		}
		PaymentDto payment = new PaymentDto(sellerData.getTransactionId(), sellerData.getAmount(), description, "https://www.google.com", "https://www.google.com");
		
		ResponseEntity<String> response = new RestTemplate().exchange(REST_API_URL + "/orders", HttpMethod.POST, new HttpEntity(payment, header), String.class);
		
		JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();
		sellerData.setUrl(jsonObject.get("payment_url").getAsString());
		return sellerData;
	}
}
