package com.microservice.bitcoin_service.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microservice.bitcoin_service.dto.PaymentDto;
import com.microservice.bitcoin_service.dto.ProductDto;
import com.microservice.bitcoin_service.dto.SellerDataDto;
import com.microservice.bitcoin_service.model.Transaction;

@Service
public class PaymentService {
	
	public static final String REST_API_URL = "https://api-sandbox.coingate.com/v2";
	
	private String success_url = "https://localhost:8083/success";
	private String cancel_url = "https://localhost:8083/cancel";
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	private OAuth2RestOperations restTemplate;
	
	@Autowired
	TransactionService transactionService;
	
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
		String order_id = UUID.randomUUID().toString();
		PaymentDto payment = new PaymentDto(sellerData.getTransactionId(), sellerData.getAmount(), description, cancel_url + "?order_id=" + order_id, success_url + "?order_id=" + order_id);
		
		ResponseEntity<String> response = new RestTemplate().exchange(REST_API_URL + "/orders", HttpMethod.POST, new HttpEntity(payment, header), String.class);
		
		JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();
		sellerData.setUrl(jsonObject.get("payment_url").getAsString());
		sellerData.setTransactionId(order_id);
		transactionService.save(new Transaction(order_id, sellerData.getClientId(), sellerData.getAmount(), new Date(), null));
		return sellerData;
	}
	
	public String success(String paymentId) {
		return sendStatus(paymentId,"SUCCESSFUL");
	}

	public String cancel(String paymentId) {

		return sendStatus(paymentId,"CANCEL");

	}

	public String sendStatus(String transactionId,String paymentStatus){

		String url = "https://sellers-service/sellers/payment/status";

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(url)
				.queryParam("transactionId",transactionId)
				.queryParam("paymentStatus",paymentStatus);

		System.out.println(paymentStatus);


		String paymentUrl = builder.build().encode().toUriString();

		System.out.println(paymentUrl);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

		ResponseEntity<String> exchange = restTemplate.exchange(paymentUrl, HttpMethod.GET, requestEntity, String.class);

		return exchange.getBody();


	}

}
