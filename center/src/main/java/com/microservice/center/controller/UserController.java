package com.microservice.center.controller;

import com.microservice.center.model.Magazine;
import com.microservice.center.model.Publication;
import com.microservice.center.model.User;
import com.microservice.center.service.Logging;
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
@RequestMapping("/user")
public class UserController {

    private Logging logging = new Logging(getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value ="/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable Long id, HttpServletRequest hr){
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + userService.getCurrentUser().getEmail() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: " + id);
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value ="/purchased",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Publication>> purchased(HttpServletRequest hr){
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + userService.getCurrentUser().getEmail() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: ");
        return new ResponseEntity<>(userService.getPurchased(), HttpStatus.OK);
    }

    @RequestMapping(value ="/subscriptions",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Magazine>> subscriptions(HttpServletRequest hr){
        logging.printInfo("ENDPOINT: " + hr.getRequestURL() + " USER: " + userService.getCurrentUser().getEmail() + " IP ADDRESS: " +
                hr.getRemoteAddr() + " PARAMETERS: ");
        return new ResponseEntity<>(userService.getSubscriptions(), HttpStatus.OK);
    }
}
