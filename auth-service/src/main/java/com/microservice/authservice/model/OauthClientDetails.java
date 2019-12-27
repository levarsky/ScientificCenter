package com.microservice.authservice.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.util.*;

@Entity
public class OauthClientDetails implements ClientDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String clientId;
    @Column
    private String clientName;
    @Column
    private String clientSecret;
    @Column
    private String authorizedGrantTypes;
    @Column
    private String authorities;
    @Column
    private String scope;
    @Column
    private String resourceIds;
    @Column
    private String webServerRedirectUri;
    @Column
    private Integer accessTokenValidity;
    @Column
    private Integer refreshTokenValidity;
    @Column
    private String additionalInformation;
    @Column
    private boolean autoapprove;

    public OauthClientDetails() {
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

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

    @Override
    public Set<String> getScope() {
        return scope != null ? new HashSet<>(Arrays.asList(scope.split(","))) : null;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes != null ? new HashSet<>(Arrays.asList(authorizedGrantTypes.split(","))) : null;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return webServerRedirectUri != null ? new HashSet<>(Arrays.asList(webServerRedirectUri.split(","))) : null;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
