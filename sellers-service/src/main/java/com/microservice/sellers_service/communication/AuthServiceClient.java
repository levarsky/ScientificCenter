package com.microservice.sellers_service.communication;


import com.microservice.sellers_service.model.OauthClientDetails;
import com.microservice.sellers_service.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

    @PostMapping(value = "/uaa/user")
    void createUser(@RequestBody User user);

    @GetMapping(value = "/uaa/user/confirm")
    String confirm(@RequestParam String token);

    @PostMapping(value = "/uaa/client")
    OauthClientDetails registerClient();

}
