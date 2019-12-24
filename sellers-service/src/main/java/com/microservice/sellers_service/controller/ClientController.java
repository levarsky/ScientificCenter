package com.microservice.sellers_service.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @RequestMapping(value="/{price}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCommunication(@PathVariable(value="price") Double price) {
       //Treba da primi predefinisani objekat koji sadrzi kolicinu novca, response je redirekcija na front za
        //izbor nacina placanja
        System.out.println("CENA: " + price);
        return null;
    }

}
