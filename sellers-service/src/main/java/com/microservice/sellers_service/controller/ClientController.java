package com.microservice.sellers_service.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController(value="/pay")
public class ClientController {

    @RequestMapping(value = "/{price}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView createCommunication(@PathVariable(value = "price") Double price) {
        //Treba da primi predefinisani objekat koji sadrzi kolicinu novca, response je redirekcija na front za
        //izbor nacina placanja
        System.out.println("Pogodio me je");
        return new ModelAndView("redirect:https://google.com");
    }

    @RequestMapping(value = "/red", method = RequestMethod.GET)
    public ModelAndView get() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:https://google.com");
        return modelAndView;
    }

}
