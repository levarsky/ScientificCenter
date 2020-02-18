package com.microservice.pay.service;

import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class SubscriptionService {
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
	
}
