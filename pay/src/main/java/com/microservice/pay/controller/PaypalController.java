package com.microservice.pay.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.pay.dto.SellerDataDto;
import com.microservice.pay.model.Transaction;
import com.microservice.pay.service.PaymentService;
import com.microservice.pay.service.TransactionService;

@RestController
@RequestMapping("/")
public class PaypalController {
	
	@Autowired
	PaymentService service;
	
	@Autowired 
	TransactionService transactionService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getToken/{username}/{password}")
	public ResponseEntity<String> getToken(@PathVariable String username, @PathVariable String password){
		return new ResponseEntity<String>(service.getToken(username, password), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/pay")
	public SellerDataDto pay(@RequestBody SellerDataDto sellerData) {
		if(sellerData.getType().equals("payment")) {
			return service.pay(sellerData);
		}
		else {
			return service.subscribe(sellerData);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "subscribeSuccess")
	public void subscribeSuccess(@RequestParam String subscription_id,HttpServletResponse httpServletResponse) throws IOException {
		Transaction transaction = transactionService.findByMerchantOrderId(subscription_id);
		transaction.setSuccessful(true);
		transactionService.save(transaction);
		httpServletResponse.sendRedirect(service.success(subscription_id));
	}

	@RequestMapping(method = RequestMethod.GET, value = "success")
	public void success(@RequestParam String paymentId,HttpServletResponse httpServletResponse) throws IOException {
		Transaction transaction = transactionService.findByMerchantOrderId(paymentId);
		transaction.setSuccessful(true);
		transactionService.save(transaction);
		httpServletResponse.sendRedirect(service.success(paymentId));
	}

	@RequestMapping(method = RequestMethod.GET, value = "subscribeCancel")
	public void subscribeCancel(@RequestParam String subscription_id,HttpServletResponse httpServletResponse) throws IOException {
		Transaction transaction = transactionService.findByMerchantOrderId(subscription_id);
		transaction.setSuccessful(false);
		transactionService.save(transaction);
		httpServletResponse.sendRedirect(service.cancel(subscription_id));
	}

	@RequestMapping(method = RequestMethod.GET, value = "cancel")
	public void cancel(@RequestParam String custom_token,HttpServletResponse httpServletResponse) throws IOException {
		Transaction transaction = transactionService.findByToken(custom_token);
		transaction.setSuccessful(false);
		transactionService.save(transaction);
		httpServletResponse.sendRedirect(service.cancel(transaction.getMerchantOrderId()));
	}
}
