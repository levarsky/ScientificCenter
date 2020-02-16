package com.microservice.bitcoin_service.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.bitcoin_service.dto.FundsTransferDto;
import com.microservice.bitcoin_service.service.AccountService;

@RestController
public class AccountController {
	@Autowired
	AccountService accountService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/accountInfo")
	public ResponseEntity<List<GDAXAccount>> getAccountInfo() throws NotAvailableFromExchangeException, NotYetImplementedForExchangeException, ExchangeException, IOException, InvalidKeyException, NoSuchAlgorithmException{
		return new ResponseEntity<List<GDAXAccount>>(accountService.getAccounts(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/transferFunds")
	public void transferFUnds(@RequestBody FundsTransferDto dto) throws InvalidKeyException, NoSuchAlgorithmException {
		accountService.transferFunds(dto);
	}
}
