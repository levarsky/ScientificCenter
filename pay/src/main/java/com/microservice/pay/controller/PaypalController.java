package com.microservice.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservice.pay.dto.SellerDataDto;
import com.microservice.pay.service.PaymentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class PaypalController {
	
	@Autowired
	PaymentService service;
	
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
		httpServletResponse.sendRedirect(service.success(subscription_id));
	}

	@RequestMapping(method = RequestMethod.GET, value = "success")
	public void success(@RequestParam String paymentId,HttpServletResponse httpServletResponse) throws IOException {
			httpServletResponse.sendRedirect(service.success(paymentId));
	}

	@RequestMapping(method = RequestMethod.GET, value = "subscribeCancel")
	public void subscribeCancel(@RequestParam String subscription_id,HttpServletResponse httpServletResponse) throws IOException {
		httpServletResponse.sendRedirect(service.cancel(subscription_id));
	}

	@RequestMapping(method = RequestMethod.GET, value = "cancel")
	public void cancel(@RequestParam String paymentId,HttpServletResponse httpServletResponse) throws IOException {
			httpServletResponse.sendRedirect(service.cancel(paymentId));
	}
}
