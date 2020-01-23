package com.microservice.authservice.service;

import com.microservice.authservice.model.OauthClientDetails;
import com.microservice.authservice.repository.OauthClientDetailsRepository;
import org.apache.commons.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


@Service
public class ClientService {

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public OauthClientDetails createClient(OauthClientDetails oauthClientDetailsSend) {

        String clientId = null;
        String secret = null;


        try {
            SecureRandom secureRandom = SecureRandom.getInstance("NativePRNG");

            byte clientBt[] = new byte[32];
            byte secretBt[] = new byte[64];
            secureRandom.nextBytes(clientBt);
            secureRandom.nextBytes(secretBt);


            Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

            clientId = encoder.encodeToString(clientBt);
            secret = encoder.encodeToString(secretBt);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        OauthClientDetails oauthClientDetails = new OauthClientDetails();

        oauthClientDetails.setClientName(oauthClientDetailsSend.getClientName());

        oauthClientDetails.setClientId(clientId);
        oauthClientDetails.setClientSecret(passwordEncoder.encode(secret));
        oauthClientDetails.setAccessTokenValidity(36000);
        oauthClientDetails.setRefreshTokenValidity(360000);
        oauthClientDetails.setScope("server");
        oauthClientDetails.setAuthorizedGrantTypes("refresh,client_credentials");

        oauthClientDetails.setAutoapprove(true);

        oauthClientDetailsRepository.save(oauthClientDetails);

        oauthClientDetails.setClientSecret(secret);


        return oauthClientDetails;

    }


}
