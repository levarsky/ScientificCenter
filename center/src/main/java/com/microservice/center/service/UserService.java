package com.microservice.center.service;

import com.microservice.center.model.Magazine;
import com.microservice.center.model.Publication;
import com.microservice.center.model.User;
import com.microservice.center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User with this id does not exists!");
        return user.get();
    }

    public List<Publication> getPurchased(){
        User user = new User();
        return user.getPurchased();
    }

    public List<Magazine> getSubscriptions(){
        User user = new User();
        return user.getSubscriptions();
    }
}
