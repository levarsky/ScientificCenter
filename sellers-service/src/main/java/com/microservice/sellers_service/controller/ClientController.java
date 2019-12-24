package com.microservice.sellers_service.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCommunication() {
       //Treba da primi predefinisani objekat koji sadrzi kolicinu novca, response je redirekcija na front za
        //izbor nacina placanja
        return null;
    }

}
