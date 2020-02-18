package com.microservice.pay.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservice.pay.config.PaypalPaymentIntent;
import com.microservice.pay.config.PaypalPaymentMethod;
import com.microservice.pay.model.PaymentDetails;
import com.microservice.pay.service.PaymentDetailsService;
import com.microservice.pay.service.PaypalService;
import com.microservice.pay.util.URLUtils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
@RequestMapping("/")
public class PaypalController {
	public static final String PAYPAL_SUCCESS_URL = "pay/success";
	public static final String PAYPAL_CANCEL_URL = "pay/cancel";

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private PaymentDetails paymentDetails;

	@Autowired
	private PaypalService paypalService;

	@Autowired
	private PaymentDetailsService paymentService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST, value = "pay")
	public String pay(HttpServletRequest request) {
		String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
		String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
		try {
			Payment payment = paypalService.createPayment(Double.parseDouble(request.getParameter("amount")), "USD",
					PaypalPaymentMethod.paypal, PaypalPaymentIntent.sale, "payment description", cancelUrl, successUrl);
			paymentDetails = new PaymentDetails();
			paymentDetails.setPayeeEmail(request.getParameter("receiver"));
			paymentDetails.setPayerEmail(request.getParameter("sender"));
			paymentDetails.setAmount(request.getParameter("amount"));
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			paymentDetails.setDate(dateFormat.format(date));
			for (Links links : payment.getLinks()) {
				if (links.getRel().equals("approval_url")) {
					return "redirect:" + links.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = PAYPAL_CANCEL_URL)
	public String cancelPay() {
		paymentDetails.setIsSuccessful(false);
		paymentService.save(paymentDetails);
		return "cancel";
	}

	@RequestMapping(method = RequestMethod.GET, value = PAYPAL_SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			paymentDetails.setAmount(payment.getTransactions().get(0).getAmount().getTotal());

			paymentDetails.setIsSuccessful(true);

			paymentService.save(paymentDetails);

			if (payment.getState().equals("approved")) {
				return "success";
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
			paymentDetails.setIsSuccessful(false);
			paymentService.save(paymentDetails);
		}
		paymentDetails.setIsSuccessful(false);
		paymentService.save(paymentDetails);
		return "redirect:/";
	}
}
