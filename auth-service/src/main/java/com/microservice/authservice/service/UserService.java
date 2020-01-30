package com.microservice.authservice.service;

import com.microservice.authservice.model.Role;
import com.microservice.authservice.model.User;
import com.microservice.authservice.model.UserDTO;
import com.microservice.authservice.model.VerificationToken;
import com.microservice.authservice.repository.RoleRepository;
import com.microservice.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailService emailService;


    public void save(User user){
        userRepository.save(user);
    }

    public void saveUser(UserDTO userDTO) {

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken!");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is registered!");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role can't be found!"));

        Set<Role> tempRoles = new HashSet<>();

        String password = passwordEncoder.encode(userDTO.getPassword());

        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());

        tempRoles.add(role);

        user.setRoles(tempRoles);
        user.setPassword(password);

        user = userRepository.save(user);

        user.setEnabled(false);
        user.setVerified(false);

        VerificationToken verificationToken = verificationTokenService.createVerificationToken(user.getUsername());

        String confirmationString  = "To confirm your account, please click here : "
                + "https://localhost:8088/registration/confirm?token=" + verificationToken.getVerificationToken();


        emailService.sendEmail(user.getEmail(),"Seller Service - Confirm your account!",confirmationString);



    }

    public User getUser(String username){
            Optional<User> user = userRepository.findByUsername(username);

            if (!user.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist!");
            return user.get();
    }


    public String confirmToken(String token) {
        return verificationTokenService.confirmToken(token);
    }
}
