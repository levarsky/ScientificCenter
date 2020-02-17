package com.microservice.bitcoin_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.bitcoin_service.dto.SellerDataDto;
import com.microservice.bitcoin_service.service.PaymentService;

@RestController
public class PaymentController {
	@Autowired
	PaymentService paymentService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/pay")
	public ResponseEntity<SellerDataDto> pay(@RequestBody SellerDataDto sellerData){
		return new ResponseEntity<SellerDataDto>(paymentService.pay(sellerData), HttpStatus.OK);
	}
}
