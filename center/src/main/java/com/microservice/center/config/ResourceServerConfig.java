package com.microservice.center.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {



    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .anonymous().and().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/pay/success*").permitAll()
                .antMatchers(HttpMethod.POST,"/pay/error*").permitAll()
                .antMatchers(HttpMethod.POST,"/pay/fail*").permitAll()
                .antMatchers(HttpMethod.POST,"/pay/cancel*").permitAll()
                .antMatchers(HttpMethod.POST,"/registration/**").permitAll()
                .antMatchers(HttpMethod.GET,"/registration/**").permitAll()
                .anyRequest().authenticated();
        // @formatter:on
    }







}
