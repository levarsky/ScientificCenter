package com.microservice.paypal_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microservice.paypal_service.dto.NameDto;
import com.microservice.paypal_service.dto.ProductCategory;
import com.microservice.paypal_service.dto.ProductDto;
import com.microservice.paypal_service.dto.ProductType;
import com.microservice.paypal_service.dto.SubscriberDto;
import com.microservice.paypal_service.dto.SubscriptionDto;
import com.microservice.paypal_service.service.SubscriptionService;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping(value = "/subscription")
public class SubscriptionController {

	@Autowired
	SubscriptionService service;
	
	@RequestMapping(method = RequestMethod.GET, value = "/plan")
	public String createSubscriptionPlan() throws PayPalRESTException {
		
		String token = service.getToken();
		
		ProductDto product = new ProductDto("PC", "Papers about PC computers.", ProductType.DIGITAL, ProductCategory.MAGAZINES, "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwixlNif0KjnAhXFwAIHHRUbDBwQjRx6BAgBEAQ&url=https%3A%2F%2Farchive.org%2Fdetails%2FPC_Magazine_February_2016&psig=AOvVaw0Z3LsJWUk-Ff3WtdvwJNBW&ust=1580381009946961");
		
		String productUrl = "https://api.sandbox.paypal.com/v1/catalogs/products";
		HttpHeaders productHeaders = new HttpHeaders();
		productHeaders.setContentType(MediaType.APPLICATION_JSON);
		productHeaders.add("Authorization", "Bearer " + token);
		
		HttpEntity productEntity = new HttpEntity(product, productHeaders);
		ResponseEntity<String> productResponse = new RestTemplate().exchange(productUrl, HttpMethod.POST, productEntity, String.class);
		return productResponse.getBody();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public String createSubscription(){
		SubscriberDto subscriber = new SubscriberDto(new NameDto("John", "Doe"), "sb-tlhk7799755@personal.example.com");
		SubscriptionDto subscription = new SubscriptionDto("P-1HX762682L049063ELYYZP3A", subscriber);
		
		String token = service.getToken();
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
				return "redirect:" + json.get("href").getAsString(); 
			}
		}
		
		return "redirect:/";
	}
}
