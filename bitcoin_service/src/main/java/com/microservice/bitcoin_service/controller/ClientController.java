package com.microservice.bitcoin_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.bitcoin_service.model.Client;
import com.microservice.bitcoin_service.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping(value="/checkUrl")
    public ResponseEntity<?> checkUrl(@RequestParam String tokenId){
        return new ResponseEntity<>( clientService.checkUrl(tokenId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registerClient(@RequestParam String tokenId,@RequestBody Client client){
        return new ResponseEntity<>(clientService.registerNewClient(client,tokenId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> registerRedirect(@RequestParam String clientId,@RequestParam String username,@RequestParam String mode){
        return new ResponseEntity<>(clientService.registerUrl(clientId,username,mode), HttpStatus.OK);
    }
}
