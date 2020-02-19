package com.microservice.center.service;

import com.microservice.center.model.*;
import com.microservice.center.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {

    @Value("${sellers.api}")
    private String sellersUrl;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserService userService;

    @Autowired
    private OAuth2RestOperations restTemplateOauth;

    public void save(Transaction transaction){
        transactionRepository.save(transaction);
    }

    public Transaction getByToken(String token){
        return transactionRepository.findByToken(token);
    }

    public List<ProductDTO> addPublications(List<Long> ids, Long transId) {
        Optional<Transaction> transaction = transactionRepository.findById(transId);
        if(!transaction.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction with this id does not exist!");

        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Long id : ids){
            Publication publication = publicationService.findById(id);
            System.out.println("DODAJEM: " + id + " u " + transId);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setAmount(publication.getPrice());
            productDTO.setName(id.toString());
            productDTOS.add(productDTO);

            transaction.get().getPublicationList().add(publication);
        }

        transactionRepository.save(transaction.get());

        return productDTOS;
    }

    public void addMagazine(Long idMagazine, Long idTransaction){
        Optional<Transaction> transaction = transactionRepository.findById(idTransaction);
        if(!transaction.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction with this id does not exist!");
        Magazine magazine = magazineService.findById(idMagazine);
        magazine.getTransactions().add(transaction.get());
        transaction.get().setMagazine(magazine);
        magazineService.save(magazine);
        transactionRepository.save(transaction.get());
    }

    public void checkTransactions(){

        List<Transaction> transactions = transactionRepository.findAllByUsername(userService.getCurrentUser().getUsername());


        for(Transaction transaction:transactions){

            if(transaction.getToken()!=null){
                if(transaction.getSuccess()==null){

                    Map<String,Object> map = (Map) getRequest(transaction.getToken());
                    String status = (String)map.get("status");

                    if(status!=null){
                        if(status.equals("CANCEL")){
                            transaction.setSuccess("cancelled");
                        }else if (status.equals("ERROR")) {
                            transaction.setSuccess("error");
                        }else if (status.equals("FAILED")){
                            transaction.setSuccess("failed");
                        }else if (status.equals("SUCCESSFUL")){
                            transaction.setSuccess("successful");
                            userService.addFromTransactionToUser(transaction);
                        }
                        transactionRepository.save(transaction);
                    }


                }
            }else{
                transactionRepository.delete(transaction);
            }

        }


    }

    public Object getRequest(String token){

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

            System.out.println(restTemplateOauth.getAccessToken());

            ResponseEntity<Object> exchange = restTemplateOauth.exchange(sellersUrl+"/token?token="+token,HttpMethod.GET, requestEntity, Object.class);

            System.out.println(exchange.getBody());

            return exchange.getBody();

    }
}
