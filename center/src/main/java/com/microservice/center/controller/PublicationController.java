package com.microservice.center.controller;

import com.microservice.center.model.Publication;
import com.microservice.center.service.Logging;
import com.microservice.center.service.PublicationService;
import com.microservice.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/publication")
public class PublicationController {

    private Logging logging = new Logging(getClass());

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private UserService userService;

    @RequestMapping(value ="/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Publication> getById(@PathVariable Long id, HttpServletRequest hr){
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + userService.getCurrentUser().getEmail() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + id);
        return new ResponseEntity<>(publicationService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buy(@PathVariable List<Long> id, HttpServletRequest hr){
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + userService.getCurrentUser().getEmail() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + id);
        publicationService.buy(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> subscribe(@PathVariable Long id, HttpServletRequest hr){
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + userService.getCurrentUser().getEmail() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + id);
        publicationService.subscribe(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
