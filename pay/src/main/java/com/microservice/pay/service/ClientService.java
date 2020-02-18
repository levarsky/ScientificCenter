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

	@Autowired
	private PaymentService paymentService;

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

		System.out.println(clientId);

		RegistrationRequest registrationRequest = registrationRequestService.getRegistrationRequest(clientId,tokenId);

		Optional<Client> optionalClient = clientRepository.findByClientId(registrationRequest.getClientId());

		String mode = registrationRequest.getMode();

		if (mode.equals("CREATE")){

			if(optionalClient.isPresent()){
				return sendStatus(registrationRequest.getClientId(),"FAILED","paypal-service");
			}

		}

		if (mode.equals("EDIT")){
			client.setId(optionalClient.get().getId());
			client.setClientId(optionalClient.get().getClientId());
		}else if (mode.equals("CREATE")){
			client.setId(null);
			client.setClientId(registrationRequest.getClientId());
		}

		paymentService.getToken(client.getPaypalClientId(),client.getPaypalSecret());


		clientRepository.save(client);

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

		Optional<Client> optionalClient = clientRepository.findByClientId(registrationRequest.getClientId());

		if(registrationRequest.getMode().equals("SHOW") || registrationRequest.getMode().equals("EDIT")){
			return optionalClient.get();
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
