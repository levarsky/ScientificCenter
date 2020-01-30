package com.microservice.center.controller;

import com.microservice.center.model.Magazine;
import com.microservice.center.model.Publication;
import com.microservice.center.model.ScentificArea;
import com.microservice.center.model.User;
import com.microservice.center.service.MagazineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/magazine")
public class MagazineController {

    @Autowired
    private MagazineService magazineService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Magazine>> getAll(){
        return new ResponseEntity<>(magazineService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value ="/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Magazine> getById(@PathVariable Long id){
        return new ResponseEntity<>(magazineService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/areas/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScentificArea>> getAreas(@PathVariable Long id){
        return new ResponseEntity<>(magazineService.getAreas(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/redactors/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getRedactors(@PathVariable Long id){
        return new ResponseEntity<>(magazineService.redactors(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/reviewers/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getReviewers(@PathVariable Long id){
        return new ResponseEntity<>(magazineService.reviewers(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/publications/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Publication>> publications(@PathVariable Long id){
        return new ResponseEntity<>(magazineService.publications(id), HttpStatus.OK);
    }
}
