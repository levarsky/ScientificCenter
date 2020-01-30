package com.microservice.authservice.model;

import com.microservice.authservice.util.SerializableObjectConverter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;


public class OauthAccessToken {

//    @Column
//    private String tokenId;
//    @Lob
//    @Column
//    private byte[] token;
//    @Column
//    private String authenticationId;
//    @Column
//    private String userName;
//    @Column
//    private String clientId;
//    @Lob
//    @Column
//    private byte[] authentication;
//    @Column
//    private String refreshToken;
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
//
//    public String getAuthenticationId() {
//        return authenticationId;
//    }
//
//    public void setAuthenticationId(String authenticationId) {
//        this.authenticationId = authenticationId;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getClientId() {
//        return clientId;
//    }
//
//    public void setClientId(String clientId) {
//        this.clientId = clientId;
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
//    public String getRefreshToken() {
//        return refreshToken;
//    }
//
//    public void setRefreshToken(String refreshToken) {
//        this.refreshToken = refreshToken;
//    }
//
//    public byte[] getToken() {
//        return token;
//    }
//
//    public void setToken(byte[] token) {
//        this.token = token;
//    }
}
