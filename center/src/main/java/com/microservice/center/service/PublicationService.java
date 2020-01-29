package com.microservice.center.service;

import com.microservice.center.model.Magazine;
import com.microservice.center.model.Publication;
import com.microservice.center.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    public Publication findById(Long id){
        Optional<Publication> publication = publicationRepository.findById(id);
        if(!publication.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Publication with this id does not exists!");
        return publication.get();
    }
}
