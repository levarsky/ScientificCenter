package com.microservice.center.service;

import com.microservice.center.model.Magazine;
import com.microservice.center.model.Publication;
import com.microservice.center.model.User;
import com.microservice.center.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MagazineService magazineService;

    public void save(Publication publication){
        publicationRepository.save(publication);
    }

    public Publication findById(Long id){
        Optional<Publication> publication = publicationRepository.findById(id);
        if(!publication.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Publication with this id does not exists!");
        return publication.get();
    }

    public void buy(List<Long> ids){
        User user = userService.getCurrentUser();
        for(Long id :ids){
            Optional<Publication> publication = publicationRepository.findById(id);
            if(!publication.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Publication with  id  " + publication.get().getId() + "does not exists!");

            user.getPurchased().add(publication.get());
            publication.get().getReaders().add(user);
            publicationRepository.save(publication.get());
        }
        userService.save(user);
    }

    public void subscribe(Long id){
        User user = userService.getCurrentUser();
        Magazine magazine = magazineService.findById(id);
        magazine.getReaders().add(user);
        user.getSubscriptions().add(magazine);
        userService.save(user);
        magazineService.save(magazine);
    }
}
