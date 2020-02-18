package com.microservice.pay.controller;


import com.microservice.pay.model.Client;
import com.microservice.pay.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping(value="/checkUrl")
    public ResponseEntity<?> checkUrl(@RequestParam String tokenId){
        clientService.checkUrl(tokenId);
        return new ResponseEntity<>( HttpStatus.OK);
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
