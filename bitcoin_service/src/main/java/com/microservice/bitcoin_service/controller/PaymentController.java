package com.microservice.bitcoin_service.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.bitcoin_service.dto.SellerDataDto;
import com.microservice.bitcoin_service.model.Transaction;
import com.microservice.bitcoin_service.service.PaymentService;
import com.microservice.bitcoin_service.service.TransactionService;

@RestController
public class PaymentController {
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	TransactionService transactionService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/pay")
	public ResponseEntity<SellerDataDto> pay(@RequestBody SellerDataDto sellerData){
		return new ResponseEntity<SellerDataDto>(paymentService.pay(sellerData), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "success")
	public void success(@RequestParam String order_id,HttpServletResponse httpServletResponse) throws IOException {
		Transaction transaction = transactionService.findByMerchantOrderId(order_id);
		transaction.setSuccessful(true);
		transactionService.save(transaction);
		httpServletResponse.sendRedirect(paymentService.success(order_id));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "cancel")
	public void cancel(@RequestParam String order_id,HttpServletResponse httpServletResponse) throws IOException {
		Transaction transaction = transactionService.findByMerchantOrderId(order_id);
		transaction.setSuccessful(false);
		transactionService.save(transaction);
		httpServletResponse.sendRedirect(paymentService.cancel(order_id));
	}
}
