package com.microservice.sellers_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.sellers_service.model.Payment;
import com.microservice.sellers_service.model.PaymentDTO;
import com.microservice.sellers_service.model.PaymentRequest;
import com.microservice.sellers_service.model.PaymentType;
import com.microservice.sellers_service.model.Product;
import com.microservice.sellers_service.model.ProductDTO;
import com.microservice.sellers_service.model.Resp;
import com.microservice.sellers_service.repository.PaymentRepository;
import com.microservice.sellers_service.repository.PaymentRequestRepository;
import com.microservice.sellers_service.repository.ProductRepository;

@Service
public class PaymentRequestService {

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientService clientService;
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private OAuth2RestOperations restTemplate;
    @Autowired
    private AuthService authService;

    public PaymentRequest saveRequest(PaymentRequest paymentRequest){
        return paymentRequestRepository.save(paymentRequest);
    }

    public PaymentRequest createRequest(PaymentDTO paymentDTO){


        Payment payment = new Payment();
        payment.setFirstName(paymentDTO.getFirstName());
        payment.setLastName(paymentDTO.getLastName());
        payment.setEmail(paymentDTO.getEmail());
        payment.setType(paymentDTO.getType());

        ArrayList<Product> products = new ArrayList<>();
        for(ProductDTO p:paymentDTO.getProducts()){
            Product product = new Product();
            product.setAmount(p.getAmount());
            product.setName(p.getName());
            product.setPayment(payment);
            products.add(product);
        }

        payment.setProducts(products);

        String token = UUID.randomUUID().toString();
        PaymentRequest request = new PaymentRequest();

        String clientId = authService.getAuth().getPrincipal().toString();

        System.out.println( authService.getAuth().getCredentials());

        request.setAmount(paymentDTO.getAmount());
        request.setClient(clientService.findByClientId(clientId));
        request.setToken(token);

        payment.setPaymentRequest(request);

        paymentRequestRepository.save(request);
        paymentRepository.save(payment);

        return request;
    }

    public Payment getPayment(Long id){
        return this.paymentRepository.findByPaymentRequestId(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id does not exist"));

    }


    public PaymentRequest getRequest(String token){
        return this.paymentRequestRepository.findByToken(token).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token does not exist"));

    }

    public PaymentRequest getRequestByTransactionId(String transactionId){
        return this.paymentRequestRepository.findByTransactionId(transactionId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction does not exist"));

    }

    public Object sendPaymentRequest(String token,Long id)  {

        PaymentRequest paymentRequest = getRequest(token);
        PaymentType paymentType = paymentTypeService.getPaymentType(id);

        Payment payment = getPayment(paymentRequest.getId());

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setProducts(new ArrayList<ProductDTO>());
        paymentDTO.setAmount(paymentRequest.getAmount());
        paymentDTO.setType(payment.getType());
        paymentDTO.setFirstName(payment.getFirstName());
        paymentDTO.setLastName(payment.getLastName());
        paymentDTO.setEmail(payment.getEmail());
        paymentDTO.setClientId(paymentRequest.getClient().getClientId());

        List<Product> products = payment.getProducts();
        for (Product p : products){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setAmount(p.getAmount());
            productDTO.setName(p.getName());
            paymentDTO.getProducts().add(productDTO);
        }

        paymentRequest.setPaymentType(paymentType);

        HttpEntity<PaymentDTO> requestEntity = new HttpEntity<>(paymentDTO);
        ResponseEntity<Object> exchange = null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("amount",paymentRequest.getAmount().toString());


        HttpHeaders httpHeadersPaypal = new HttpHeaders();
        httpHeadersPaypal.setContentType(MediaType.APPLICATION_JSON);
        
        ProductDTO productDto = new ProductDTO();
//        productDto.setName(magazineName);
//        productDto.setType(magazineType);
//        productDto.setDescription("/");
//        productDto.setSubscriberGivenName(userGivenName);
//        productDto.setSubscriberSurname(userSurname);
//        productDto.setSubscriberEmail(userEmail);
//        productDto.setAmount(paymentRequest.getAmount());
//        productDto.setClientId(paymentRequest.getClient().getClientId());


        
        if(!paymentType.getServiceName().equals("paypal-service")){
            exchange = restTemplate.exchange("https://"+paymentType.getServiceName()+"/pay", HttpMethod.POST, requestEntity, Object.class);
        }else{
        	exchange = restTemplate.exchange("https://"+paymentType.getServiceName()+"/pay", HttpMethod.POST, new HttpEntity<>(paymentDTO, httpHeadersPaypal),Object.class);

        }

        //PaymentRequest paymentRequest1 = this.bankPaymentServices.create(paymentRequest);
        
        //System.out.println(exchange.getBody());
        System.out.println(exchange.getBody());

        ObjectMapper mapper = new ObjectMapper();
        PaymentDTO paymentDTOReceived = mapper.convertValue(exchange.getBody(), PaymentDTO.class);

        paymentRequest.setTransactionId(paymentDTOReceived.getTransactionId());
        paymentRequestRepository.save(paymentRequest);

        return exchange.getBody();
    }


}
