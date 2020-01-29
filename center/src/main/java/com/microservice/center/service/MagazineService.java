package com.microservice.center.service;

import com.microservice.center.model.Magazine;
import com.microservice.center.model.Publication;
import com.microservice.center.model.ScentificArea;
import com.microservice.center.model.User;
import com.microservice.center.repository.MagazineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MagazineService {

    @Autowired
    private MagazineRepository magazineRepository;

    public List<Magazine> getAll(){
        return magazineRepository.findAll();
    }

    public Magazine findById(Long id) {
        Optional<Magazine> magazine = magazineRepository.findById(id);
        if(!magazine.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Magazine with this id does not exists!");
        return magazine.get();
    }

    public List<ScentificArea> getAreas(Long id){
        Optional<Magazine> magazine = magazineRepository.findById(id);
        if(!magazine.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Magazine with this id does not exists!");
        return magazine.get().getScientificArea();
    }

    public List<User> redactors(Long id){
        Optional<Magazine> magazine = magazineRepository.findById(id);
        if(!magazine.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Magazine with this id does not exists!");
        return magazine.get().getRedactors();
    }

    public List<User> reviewers(Long id){
        Optional<Magazine> magazine = magazineRepository.findById(id);
        if(!magazine.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Magazine with this id does not exists!");
        return magazine.get().getReviewer();
    }

    public List<Publication> publications(Long id) {
        Optional<Magazine> magazine = magazineRepository.findById(id);
        if(!magazine.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Magazine with this id does not exists!");

        return magazine.get().getPublications();
    }

}
