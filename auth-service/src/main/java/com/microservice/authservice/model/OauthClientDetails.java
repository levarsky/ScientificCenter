package com.microservice.authservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.util.*;


public class OauthClientDetails implements ClientDetails {



    private String clientId;

    private String clientSecret;

    private String authorizedGrantTypes;

    private String authorities;

    private String scope;

    private String resourceIds;

    private String webServerRedirectUri;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private boolean autoapprove;

    public OauthClientDetails() {
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @JsonIgnore
    @Override
    public Set<String> getResourceIds() {
        return resourceIds != null ? new HashSet<>(Arrays.asList(resourceIds.split(","))) : null;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @JsonIgnore
    @Override
    public Set<String> getScope() {
        return scope != null ? new HashSet<>(Arrays.asList(scope.split(","))) : null;
    }

    @JsonIgnore
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes != null ? new HashSet<>(Arrays.asList(authorizedGrantTypes.split(","))) : null;
    }

    @JsonIgnore
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return webServerRedirectUri != null ? new HashSet<>(Arrays.asList(webServerRedirectUri.split(","))) : null;
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValidity;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return autoapprove;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public void setAutoapprove(boolean autoapprove) {
        this.autoapprove = autoapprove;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

}
