package com.microservice.center.controller;

import com.microservice.center.model.Resp;
import com.microservice.center.model.Transaction;
import com.microservice.center.service.HttpClient;
import com.microservice.center.service.PaymentService;
import com.microservice.center.service.TransactionService;
import com.microservice.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @RequestMapping(value= "/{amount}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resp> testController(@PathVariable(value="amount") Double amount, @RequestBody List<Long> ids, HttpServletRequest hr) throws Exception {

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTimestamp(new Date());
        transaction.setIpAddress(hr.getRemoteAddr());
        transaction.setUsername(userService.getCurrentUser().getUsername());
        transactionService.save(transaction);

        String requestToken = this.paymentService.getToken(amount);
        System.out.println(requestToken);
        String url = "https://localhost:8088/sellers/pay/request?token="+requestToken;

        transaction.setToken(requestToken);
        transactionService.addPublications(ids,transaction.getId());
        transactionService.save(transaction);

        Resp r = new Resp(url);
        return new ResponseEntity<>(r ,HttpStatus.OK);
    }

    @RequestMapping(value= "/subscribe/{amount}/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resp> subscribe(@PathVariable(value="amount") Double amount,@PathVariable(value="id") Long id, HttpServletRequest hr) throws Exception {

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTimestamp(new Date());
        transaction.setIpAddress(hr.getRemoteAddr());
        transaction.setUsername(userService.getCurrentUser().getUsername());
        transactionService.save(transaction);

        String requestToken = this.paymentService.getToken(amount);
        System.out.println(requestToken);
        String url = "https://localhost:8088/sellers/pay/request?token="+requestToken;

        //transaction.setToken(requestToken);
        //transactionService.save(transaction);
        Resp r = new Resp(url);
        transactionService.addMagazine(id,transaction.getId());
        transactionService.save(transaction);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @RequestMapping(value= "/success",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentSuccess(@RequestParam String requestId) throws Exception {
        Transaction transaction = transactionService.getByToken(requestId);
        if(transaction != null){
            transaction.setSuccess("successful");
            transactionService.save(transaction);
            userService.addFromTransactionToUser(transaction);
        }
        return new ResponseEntity<>(paymentService.paymentSuccess(requestId),HttpStatus.OK);
    }

    @RequestMapping(value= "/fail",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentFail(@RequestParam String requestId) throws Exception {
        Transaction transaction = transactionService.getByToken(requestId);
        if(transaction != null){
            transaction.setSuccess("failed");
            transactionService.save(transaction);
        }
        return new ResponseEntity<>(paymentService.paymentFail(requestId),HttpStatus.OK);
    }

    @RequestMapping(value= "/error",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentError(@RequestParam String requestId) throws Exception {
        Transaction transaction = transactionService.getByToken(requestId);
        if(transaction != null){
            transaction.setSuccess("error");
            transactionService.save(transaction);
        }
        return new ResponseEntity<>(paymentService.paymentError(requestId),HttpStatus.OK);
    }

    @RequestMapping(value= "/cancel",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentCancel(@RequestParam String requestId) throws Exception {
        Transaction transaction = transactionService.getByToken(requestId);
        if(transaction != null){
            transaction.setSuccess("canceled");
            transactionService.save(transaction);
        }
        return new ResponseEntity<>(paymentService.paymentCancel(requestId),HttpStatus.OK);
    }
}
