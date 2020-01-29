package com.microservice.bitcoin_service.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.bitcoin_service.dao.TransactionDao;
import com.microservice.bitcoin_service.model.Transaction;
import com.microservice.bitcoin_service.service.TransactionService;

@RestController
public class TransactionController {
	@Autowired
	TransactionService service;
	
	@RequestMapping(value = "/transfer", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Transaction> transfer(@RequestBody TransactionDao dao){
		String result = service.transfer(dao.getSeedSender(), dao.getAmount(), dao.getSeedReceiver());
		Transaction tr = new Transaction();
		tr.setSeedReceiver(dao.getSeedReceiver());
		tr.setSeedSender(dao.getSeedSender());
		tr.setAmount(dao.getAmount());
		if(result == "succeed") {
			tr.setIsSuccessful(true);
		}
		else {
			tr.setIsSuccessful(false);
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		tr.setDate(dateFormat.format(date));
		
		service.save(tr);
		
		return new ResponseEntity<Transaction>(tr, HttpStatus.OK);
	}
}
