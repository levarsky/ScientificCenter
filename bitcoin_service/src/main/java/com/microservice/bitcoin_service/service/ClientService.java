package com.microservice.bitcoin_service.service;

import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.microservice.bitcoin_service.model.Client;
import com.microservice.bitcoin_service.model.RegistrationRequest;
import com.microservice.bitcoin_service.repository.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	ClientRepository clientRepo;
	
	@Autowired
	private RegistrationRequestService registrationRequestService;
	
	@Autowired
	private OAuth2RestOperations restTemplate;

	@Autowired
	private AuthService authService;
	
	private String registrationForm = "http://localhost:4207/registrationRequest";
	
	public Client findByClientId(String clientId) {
		return clientRepo.findByClientId(clientId);
	}
	
	public Object registerNewClient(Client client,String tokenId){

		System.out.println(client);

		String clientId = authService.getAuth().getPrincipal().toString();

		System.out.println(clientId);

		RegistrationRequest registrationRequest = registrationRequestService.getRegistrationRequest(clientId,tokenId);

		Client optionalClient = clientRepo.findByClientId(registrationRequest.getClientId());

		String mode = registrationRequest.getMode();

		if (mode.equals("CREATE")){

			if(optionalClient != null){
				return sendStatus(registrationRequest.getClientId(),"FAILED","paypal-service");
			}

		}

		if (mode.equals("EDIT")){
			client.setId(optionalClient.getId());
			client.setClientId(optionalClient.getClientId());
		}else if (mode.equals("CREATE")){
			client.setId(null);
			client.setClientId(registrationRequest.getClientId());
		}


		clientRepo.save(client);

		return sendStatus(registrationRequest.getClientId(),"SUCCESSFUL","paypal-service");

	}


	public Object registerUrl(String clientId,String username,String mode) {

		RegistrationRequest registrationRequest = new RegistrationRequest();

		registrationRequest.setAccessed(false);
		registrationRequest.setClientId(clientId);
		registrationRequest.setUsername(username);
		registrationRequest.setMode(mode);

		String token = generateRandomPaymentId();

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(registrationForm)
				.queryParam("tokenId",token);


		if(mode.equals("SHOW"))
			builder.queryParam("show",true);
		else if(mode.equals("EDIT"))
			builder.queryParam("edit",true);

		registrationRequest.setTokenId(token);

		registrationRequestService.save(registrationRequest);

		return Collections.singletonMap("redirectUrl",builder.build().encode().toUriString());
	}

	private String generateRandomPaymentId() {
		String generatedString = UUID.randomUUID().toString();
		return generatedString;
	}

	public Object checkUrl(String tokenId) {

		String clientId = authService.getAuth().getPrincipal().toString();

		System.out.print(clientId);

		RegistrationRequest registrationRequest = registrationRequestService.getRegistrationRequest(clientId,tokenId);

		if (registrationRequest.isAccessed()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid!");
		}

		registrationRequest.setAccessed(true);
		registrationRequestService.save(registrationRequest);

		Client optionalClient = clientRepo.findByClientId(registrationRequest.getClientId());

		if(registrationRequest.getMode().equals("SHOW") || registrationRequest.getMode().equals("EDIT")){
			return optionalClient;
		}

		return null;
	}

	public String sendStatus(String clientId,String status,String paymentService){

		String url = "https://sellers-service/sellers/client/status";

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(url)
				.queryParam("clientId",clientId)
				.queryParam("status",status)
				.queryParam("paymentService",paymentService);


		String paymentUrl = builder.build().encode().toUriString();

		System.out.println(paymentUrl);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

		ResponseEntity<String> exchange = restTemplate.exchange(paymentUrl, HttpMethod.GET, requestEntity, String.class);

		return exchange.getBody();

	}
}
