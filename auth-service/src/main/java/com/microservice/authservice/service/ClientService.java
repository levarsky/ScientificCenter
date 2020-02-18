package com.microservice.authservice.service;

import com.microservice.authservice.config.JDBCTokenConfig;
import com.microservice.authservice.model.OauthClientDetails;
import org.apache.commons.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


@Service
public class ClientService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;


    public OauthClientDetails createClient() {

        String clientId = null;
        String secret = null;

        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

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

        oauthClientDetails.setClientId(clientId);
        oauthClientDetails.setClientSecret(passwordEncoder.encode(secret));
        oauthClientDetails.setAccessTokenValidity(36000);
        oauthClientDetails.setRefreshTokenValidity(360000);
        oauthClientDetails.setScope("server");
        oauthClientDetails.setAuthorizedGrantTypes("refresh_token,client_credentials");

        oauthClientDetails.setAutoapprove(true);

        jdbcClientDetailsService.addClientDetails(oauthClientDetails);

        oauthClientDetails.setClientSecret(secret);

        return oauthClientDetails;
    }


}
