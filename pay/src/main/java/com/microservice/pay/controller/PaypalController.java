package com.microservice.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.microservice.pay.dto.Resp;
import com.microservice.pay.dto.SellerDataDto;
import com.microservice.pay.service.PaymentService;

@Controller
@RequestMapping("/")
public class PaypalController {
	
	@Autowired
	PaymentService service;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getToken/{username}/{password}")
	public ResponseEntity<String> getToken(@PathVariable String username, @PathVariable String password){
		return new ResponseEntity<String>(service.getToken(username, password), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/pay")
	public Resp pay(@RequestBody SellerDataDto sellerData) {
		String token = null;
		if(sellerData.getType().equals("payment")) {
			token = service.pay(sellerData);
		}
		else {
			
		}
		
		return null;
	}
}
