package com.microservice.center.service;

import com.microservice.center.model.Magazine;
import com.microservice.center.model.Publication;
import com.microservice.center.model.Transaction;
import com.microservice.center.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private MagazineService magazineService;

    public void save(Transaction transaction){
        transactionRepository.save(transaction);
    }

    public Transaction getByToken(String token){
        return transactionRepository.findByToken(token);
    }

    public void addPublications(List<Long> ids, Long transId) {
        Optional<Transaction> transaction = transactionRepository.findById(transId);
        if(!transaction.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction with this id does not exist!");

        for(Long id : ids){
            Publication publication = publicationService.findById(id);
            transaction.get().getPublicationList().add(publication);
            publicationService.save(publication);
        }

        transactionRepository.save(transaction.get());
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
}
