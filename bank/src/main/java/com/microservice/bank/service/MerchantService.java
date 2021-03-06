package com.microservice.bank.service;

import com.microservice.bank.model.Account;
import com.microservice.bank.model.Merchant;
import com.microservice.bank.model.MerchantDTO;
import com.microservice.bank.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class MerchantService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MerchantRepository merchantRepository;

    public Account getAccountFromMerchant(String merchantId,String merchantPassword){
       Optional<Merchant> merchant = merchantRepository.findByMerchantIdAndMerchantPassword(merchantId.trim(),merchantPassword.trim());
       System.out.println(merchantId + " " + merchantPassword);
       if (!merchant.isPresent())
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid details !");
       return merchant.get().getAccount();
    }

    public MerchantDTO getMerchantDetails(Account account){

        Account optionalAccount = accountService.getAccount(account.getCardNumber(),account.getCvv(),account.getCardHolderName(),account.getExpirationDate());


        if (optionalAccount==null){
            //PROVERA U DRUGOJ BANCI
        }

        if (merchantRepository.existsByAccount_Id(optionalAccount.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merchant details exist!");


        Merchant merchant = new Merchant();


        String merchantId = null;
        String merchantPassword = null;

        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

            byte clientBt[] = new byte[20];
            byte secretBt[] = new byte[60];
            secureRandom.nextBytes(clientBt);
            secureRandom.nextBytes(secretBt);

            Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

            merchantId = encoder.encodeToString(clientBt);
            merchantPassword = encoder.encodeToString(secretBt);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        merchant.setMerchantId(merchantId);
        merchant.setMerchantPassword(merchantPassword);
        merchant.setAccount(optionalAccount);

        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setMerchantId(merchantId);
        merchantDTO.setMerchantPassword(merchantPassword);

        merchantRepository.save(merchant);

        return merchantDTO;
    }

}
