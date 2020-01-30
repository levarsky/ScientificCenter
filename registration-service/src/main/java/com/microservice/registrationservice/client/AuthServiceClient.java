package com.microservice.registrationservice.client;

import com.microservice.registrationservice.model.OauthClientDetails;
import com.microservice.registrationservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

    @PostMapping(value = "/uaa/user")
    User createUser(@RequestBody User user);

    @GetMapping(value = "/uaa/confirm")
    String confirm(@RequestParam String token);

    @PostMapping(value = "/uaa/client")
    OauthClientDetails registerClient(@RequestBody OauthClientDetails oauthClientDetails);

}
