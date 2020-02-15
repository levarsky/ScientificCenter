package com.microservice.pay.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microservice.pay.dto.*;
import com.microservice.pay.model.Client;
import com.microservice.pay.model.User;
import com.microservice.pay.service.ClientService;
import com.microservice.pay.service.SubscriptionService;
import com.microservice.pay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/subscription")
public class SubscriptionController {

	@Autowired
	SubscriptionService service;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public Resp createSubscription(@RequestBody ProductDTO productDto){
		
		Client client = clientService.findByClientId(productDto.getClientId());
		String token = service.getToken(client.getPaypalClientId(), client.getPaypalSecret());
		PaypalProductDto product = new PaypalProductDto(productDto.getName(), "DIGITAL");
		User user = userService.findByUserEmail(productDto.getSubscriberEmail());
		SubscriberDto subscriber = new SubscriberDto(new NameDto(productDto.getSubscriberGivenName(), productDto.getSubscriberSurname()), user.getPaypalUserEmail());
		
		String productUrl = "https://api.sandbox.paypal.com/v1/catalogs/products";
		HttpHeaders productHeaders = new HttpHeaders();
		productHeaders.setContentType(MediaType.APPLICATION_JSON);
		productHeaders.add("Authorization", "Bearer " + token);
		HttpEntity productEntity = new HttpEntity(product, productHeaders);
		
		ResponseEntity<String> productResponse = new RestTemplate().exchange(productUrl, HttpMethod.POST, productEntity, String.class);
		JsonObject productJson = new JsonParser().parse(productResponse.getBody()).getAsJsonObject();
		
		PlanDto plan = new PlanDto();
		Frequency frequency = new Frequency("MONTH", 1);
		BillingCycle billingCycleTrial = new BillingCycle(frequency, "TRIAL", 1, 1, new PricingScheme(new FixedPrice("10", "USD")));
		BillingCycle billingCycleRegular = new BillingCycle(frequency, "REGULAR", 2, 12, new PricingScheme(new FixedPrice("100", "USD")));
		List<BillingCycle> billingCycles = new ArrayList<>();
		billingCycles.add(billingCycleTrial);
		billingCycles.add(billingCycleRegular);
		plan.setName(productDto.getName() + "_Plan");
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
		
		Resp resp = new Resp("");
		JsonObject jsonObject = new JsonParser().parse(subscriptionResponse.getBody()).getAsJsonObject();
		for(JsonElement link : jsonObject.get("links").getAsJsonArray()) {
			JsonObject json = link.getAsJsonObject();
			if(json.get("rel").getAsString().equals("approve")) {
				resp.setUrl(json.get("href").getAsString());
				return resp;
			}
		}
		
		return resp;
	}
}
