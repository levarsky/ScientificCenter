package com.microservice.authservice.model;

import com.microservice.authservice.util.SerializableObjectConverter;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;
import java.sql.Blob;


public class OauthRefreshToken {

//    public static final String TOKEN_ID = "tokenId";
//
//    @Column
//    private String tokenId;
//    @Lob
//    private byte[] token;
//    @Lob
//    private byte[] authentication;
//
//
//    public String getTokenId() {
//        return tokenId;
//    }
//
//    public void setTokenId(String tokenId) {
//        this.tokenId = tokenId;
//    }
//
//    public byte[] getToken() {
//        return token;
//    }
//
//    public void setToken(byte[] token) {
//        this.token = token;
//    }
//
//    public byte[] getAuthentication() {
//        return authentication;
//    }
//
//    public void setAuthentication(byte[] authentication) {
//        this.authentication = authentication;
//    }
//
//
}
