package com.microservice.pay.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microservice.pay.dto.ApplicationContext;
import com.microservice.pay.dto.BillingCycle;
import com.microservice.pay.dto.FixedPrice;
import com.microservice.pay.dto.Frequency;
import com.microservice.pay.dto.ItemDto;
import com.microservice.pay.dto.ItemListDto;
import com.microservice.pay.dto.NameDto;
import com.microservice.pay.dto.PaymentDto;
import com.microservice.pay.dto.PaymentPreferences;
import com.microservice.pay.dto.PaypalProductDto;
import com.microservice.pay.dto.PlanDto;
import com.microservice.pay.dto.PricingScheme;
import com.microservice.pay.dto.ProductDTO;
import com.microservice.pay.dto.Resp;
import com.microservice.pay.dto.SellerDataDto;
import com.microservice.pay.dto.SubscriberDto;
import com.microservice.pay.dto.SubscriptionDto;
import com.microservice.pay.dto.TransactionDto;
import com.microservice.pay.model.Client;

@Service
public class PaymentService {
	
	private static String PAYMENT_URL = "https://api.sandbox.paypal.com/v1/payments/payment";
	
	@Autowired
	ClientService clientService;
	
	public String getToken(String username, String password) {
		
		String url = "https://api.sandbox.paypal.com/v1/oauth2/token";

		//String username = "AQ-JUhmxs9ZQU619meknUmn6hbJoafvclUDuzcgK51w3js8VHvFK_eQOWs_i-YiBxz_nwbztqj6w_sfX";
		//String password = "EKa_NIeCT-zhIbw_ZgQm8z-zEoQJUzPGD8AncNR1wuo9jzNKrt5sQBD58YglYNalV7cJKo3vCaCLoKEd";
		String authStr = username + ":" + password;

		String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "Basic " + base64Creds);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "client_credentials");

		HttpEntity request = new HttpEntity(map, headers);
		System.out.println(request);
		ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.POST, request, String.class);
		String json = response.getBody();
		JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
		String token = jsonObject.get("access_token").getAsString();
		
		return token;
	}
	
	public SellerDataDto pay(SellerDataDto sellerData) {
		Client client = clientService.findByClientId(sellerData.getClientId());
		String token = this.getToken(client.getPaypalClientId(), client.getPaypalSecret());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("Authorization", "Bearer " + token);
		
		List<TransactionDto> transactions = new ArrayList<TransactionDto>();
		List<ItemDto> items = new ArrayList<ItemDto>();
		for(ProductDTO product : sellerData.getProducts()) {
			items.add(new ItemDto(product.getName(), String.valueOf(product.getAmount())));
		}
		transactions.add(new TransactionDto(new ItemListDto(items)));
		
		ApplicationContext redirect_urls = new ApplicationContext("https://www.google.com", "https://www.google.com");
		
		PaymentDto paymentDto = new PaymentDto(transactions, redirect_urls);
		
		ResponseEntity<String> response = new RestTemplate().exchange(PAYMENT_URL, HttpMethod.POST, new HttpEntity(paymentDto, httpHeaders), String.class);
		Resp resp = new Resp("");
		JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();
		for(JsonElement link : jsonObject.get("links").getAsJsonArray()) {
			JsonObject json = link.getAsJsonObject();
			if(json.get("rel").getAsString().equals("approval_url")) {
				resp.setUrl(json.get("href").getAsString());
			}
		}
		sellerData.setUrl(resp.getUrl());
		return sellerData;
	}
	
	public SellerDataDto subscribe(SellerDataDto sellerData) {
		Client client = clientService.findByClientId(sellerData.getClientId());
		String token = this.getToken(client.getPaypalClientId(), client.getPaypalSecret());
		
		PaypalProductDto product = new PaypalProductDto(sellerData.getProducts().get(0).getName(), "DIGITAL");
		SubscriberDto subscriber = new SubscriberDto(new NameDto(sellerData.getFirstName(), sellerData.getLastName()), sellerData.getEmail());
		
		String productUrl = "https://api.sandbox.paypal.com/v1/catalogs/products";
		HttpHeaders productHeaders = new HttpHeaders();
		productHeaders.setContentType(MediaType.APPLICATION_JSON);
		productHeaders.add("Authorization", "Bearer " + token);
		HttpEntity productEntity = new HttpEntity(product, productHeaders);
		
		ResponseEntity<String> productResponse = new RestTemplate().exchange(productUrl, HttpMethod.POST, productEntity, String.class);
		JsonObject productJson = new JsonParser().parse(productResponse.getBody()).getAsJsonObject();
		
		PlanDto plan = new PlanDto();
		Frequency frequency = new Frequency("MONTH", 1);
		BillingCycle billingCycleTrial = new BillingCycle(frequency, "TRIAL", 1, 1, new PricingScheme(new FixedPrice(String.valueOf(sellerData.getProducts().get(0).getAmount()), "USD")));
		BillingCycle billingCycleRegular = new BillingCycle(frequency, "REGULAR", 2, 12, new PricingScheme(new FixedPrice("100", "USD")));
		List<BillingCycle> billingCycles = new ArrayList<>();
		billingCycles.add(billingCycleTrial);
		billingCycles.add(billingCycleRegular);
		plan.setName(sellerData.getProducts().get(0).getName() + "_Plan");
		plan.setProduct_id(productJson.get("id").getAsString());
		plan.setBilling_cycles(billingCycles);
		plan.setPayment_preferences(new PaymentPreferences(true));
		
		String planUrl = "https://api.sandbox.paypal.com/v1/billing/plans";
		HttpHeaders planHeaders = new HttpHeaders();
		planHeaders.setContentType(MediaType.APPLICATION_JSON);
		planHeaders.add("Authorization", "Bearer " + token);
		HttpEntity planEntity = new HttpEntity(plan, planHeaders);
		
		ResponseEntity<String> planResponse = new RestTemplate().exchange(planUrl, HttpMethod.POST, planEntity, String.class);
		JsonObject planJson = new JsonParser().parse(planResponse.getBody()).getAsJsonObject();
		
		ApplicationContext appCon = new ApplicationContext("https://www.google.com", "https://www.google.com");
		SubscriptionDto subscription = new SubscriptionDto(planJson.get("id").getAsString(), subscriber, appCon);
		
		String subscriptionUrl = "https://api.sandbox.paypal.com/v1/billing/subscriptions";
		HttpHeaders subscriptionHeaders = new HttpHeaders();
		subscriptionHeaders.setContentType(MediaType.APPLICATION_JSON);
		subscriptionHeaders.add("Authorization", "Bearer " + token);
		
		HttpEntity subscriptionEntity = new HttpEntity(subscription, subscriptionHeaders);
		ResponseEntity<String> subscriptionResponse = new RestTemplate().exchange(subscriptionUrl, HttpMethod.POST, subscriptionEntity, String.class);
		
		JsonObject jsonObject = new JsonParser().parse(subscriptionResponse.getBody()).getAsJsonObject();
		for(JsonElement link : jsonObject.get("links").getAsJsonArray()) {
			JsonObject json = link.getAsJsonObject();
			if(json.get("rel").getAsString().equals("approve")) {
				sellerData.setUrl(json.get("href").getAsString());
			}
		}
		
		return sellerData;
	}
	
}
