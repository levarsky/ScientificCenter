package com.microservice.pay.service;

import com.microservice.pay.model.Client;
import com.microservice.pay.model.RegistrationRequest;
import com.microservice.pay.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

	@Autowired
	private RegistrationRequestService registrationRequestService;

	@Autowired
	ClientRepo clientRepository;

	@Autowired
	private OAuth2RestOperations restTemplate;

	@Autowired
	private AuthService authService;

	private String registrationForm = "http://localhost:4207/registrationRequest";

	public Client findByClientId(String clientId){
		Optional<Client> clientOptional = clientRepository.findByClientId(clientId);
		if (!clientOptional.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client doesn't exist!");
		return clientOptional.get();
	}

	public Object registerNewClient(Client client,String tokenId){

		System.out.println(client);

		String clientId = authService.getAuth().getPrincipal().toString();

		RegistrationRequest registrationRequest = registrationRequestService.getRegistrationRequest(clientId,tokenId);

		Optional<Client> optionalClient = clientRepository.findByClientId(clientId);

		String mode = registrationRequest.getMode();

		if (mode.equals("CREATE")){

			if(optionalClient.isPresent()){

				return sendStatus(clientId,"SUCCESSFUL","paypal-service");
			}


		}

		if (mode.equals("EDIT")){
			client.setId(optionalClient.get().getId());
		}else if (mode.equals("CREATE")){
			client.setId(null);
			client.setClientId(registrationRequest.getClientId());
		}

		clientRepository.save(client);

		return sendStatus(clientId,"SUCCESSFUL","paypal-service");

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

		registrationRequest.setTokenId(token);

		registrationRequestService.save(registrationRequest);

		return Collections.singletonMap("redirectUrl",builder.build().encode().toUriString());
	}

	private String generateRandomPaymentId() {
		String generatedString = UUID.randomUUID().toString();
		return generatedString;
	}

	public void checkUrl(String tokenId) {

		String clientId = authService.getAuth().getPrincipal().toString();

		System.out.print(clientId);

		RegistrationRequest registrationRequest = registrationRequestService.getRegistrationRequest(clientId,tokenId);

		if (registrationRequest.isAccessed()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid!");
		}

		registrationRequest.setAccessed(true);
		registrationRequestService.save(registrationRequest);

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
