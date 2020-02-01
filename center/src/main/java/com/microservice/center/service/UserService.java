package com.microservice.center.service;

import com.microservice.center.model.*;
import com.microservice.center.repository.RoleRepository;
import com.microservice.center.repository.TransactionRepository;
import com.microservice.center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private MagazineService magazineService;

    public void save(User user){
        userRepository.save(user);
    }

    public UserDTO saveUser(UserDTO userDTO) {

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

        user.setCity(userDTO.getCity());
        user.setCountry(userDTO.getCountry());
        user.setTitle(user.getTitle());

        tempRoles.add(role);

        user.setRoles(tempRoles);
        user.setPassword(password);

        user = userRepository.save(user);

        user.setEnabled(false);
        user.setVerified(false);

        VerificationToken verificationToken = verificationTokenService.createVerificationToken(user.getUsername());

        String confirmationString  = "To confirm your account, please click here : "
                + "https://localhost:8080/center/registration/confirm?token=" + verificationToken.getVerificationToken();


        emailService.sendEmail(user.getEmail(),"Scientific Center - Confirm your account!",confirmationString);

        userDTO.setPassword("");

        return userDTO;

    }

    public void addFromTransactionToUser(Transaction transaction){
        User user = getUser(transaction.getUsername());
        if(transaction.getMagazine() != null){
            user.getSubscriptions().add(transaction.getMagazine());
            transaction.getMagazine().getReaders().add(user);
            userRepository.save(user);
            magazineService.save(transaction.getMagazine());
        }

        if(!transaction.getPublicationList().isEmpty()){
            for(Publication p : transaction.getPublicationList()){
                user.getPurchased().add(p);
                p.getReaders().add(user);
                publicationService.save(p);
            }
            userRepository.save(user);
        }
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

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getName());
        return user.get();
    }

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
