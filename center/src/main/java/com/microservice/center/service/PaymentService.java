package com.microservice.center.service;

import com.microservice.center.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private OAuth2RestOperations restTemplateOauth;

    @Value("${sellers.api}")
    private String sellersUrl;

    @Value("${center.front}")
    private String frontUrl;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    MagazineService magazineService;

    public String getToken(PaymentDTO paymentDTO){

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<>(paymentDTO);

        System.out.println(restTemplateOauth.getAccessToken());

        ResponseEntity<String> exchange = restTemplateOauth.exchange(sellersUrl,HttpMethod.POST, requestEntity, String.class);

        System.out.println(exchange.toString());

        return exchange.getBody();
    }

    public String paymentSuccess(String requestId){
        return frontUrl+"?success="+requestId;
    }

    public String paymentFail(String requestId){
        return frontUrl+"?fail="+requestId;
    }

    public String paymentError(String requestId){
        return frontUrl+"?error="+requestId;
    }

    public String paymentCancel(String requestId){
        return frontUrl+"?cancel="+requestId;
    }

    public Object pay(Double amount,List<Long> longList,HttpServletRequest hr){

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTimestamp(new Date());
        transaction.setIpAddress(hr.getRemoteAddr());
        User user = userService.getCurrentUser();
        transaction.setUsername(user.getUsername());
        transactionService.save(transaction);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(amount);
        paymentDTO.setFirstName(user.getFirstName());
        paymentDTO.setLastName(user.getLastName());
        paymentDTO.setEmail(user.getEmail());
        paymentDTO.setType("payment");


        List<ProductDTO> productDTOS = transactionService.addPublications(longList,transaction.getId());
        paymentDTO.setProducts(productDTOS);

        String requestToken = getToken(paymentDTO);
        System.out.println(requestToken);
        String url = sellersUrl + "/request?token="+requestToken;

        transaction.setToken(requestToken);

        transactionService.save(transaction);

        Resp r = new Resp(url);
        return r;
    }

    public Object subscribe(Double amount, Long id, HttpServletRequest hr) {

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTimestamp(new Date());
        transaction.setIpAddress(hr.getRemoteAddr());
        transaction.setUsername(userService.getCurrentUser().getUsername());
        transactionService.save(transaction);

        PaymentDTO paymentDTO = new PaymentDTO();

        Magazine magazine = magazineService.findById(id);
        User user = userService.getCurrentUser();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(id.toString());
        productDTO.setAmount(magazine.getPrice());
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        productDTOS.add(productDTO);

        System.out.println("Subs  "+ amount);

        paymentDTO.setAmount(amount);
        paymentDTO.setFirstName(user.getFirstName());
        paymentDTO.setLastName(user.getLastName());
        paymentDTO.setEmail(user.getEmail());
        paymentDTO.setType("subscription");
        paymentDTO.setProducts(productDTOS);

        String requestToken = getToken(paymentDTO);
        System.out.println(requestToken);
        String url = sellersUrl + "/request?token="+requestToken;

        transaction.setToken(requestToken);
        transactionService.save(transaction);
        Resp r = new Resp(url);
        transactionService.addMagazine(id,transaction.getId());
        transactionService.save(transaction);
        return r;
    }
}
