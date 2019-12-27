package com.microservice.bitcoin_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.bitcoin_service.dao.TransactionDao;
import com.microservice.bitcoin_service.service.TransactionService;

@RestController
public class TransactionController {
	@Autowired
	TransactionService service;
	
	@RequestMapping(value = "/transfer", method = RequestMethod.POST, consumes = "application/json", produces = "plain/text")
	public ResponseEntity<String> transfer(@RequestBody TransactionDao dao){
		return new ResponseEntity<String>(service.transfer(dao.getSeedSender(), dao.getAmount(), dao.getSeedReceiver()), HttpStatus.OK);
	}
}
