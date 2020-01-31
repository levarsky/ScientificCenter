package com.sep.pcc.controller;

import com.sep.pcc.model.RequestFromBank;
import com.sep.pcc.model.ResponseFromBank;
import com.sep.pcc.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseFromBank> request(@RequestBody RequestFromBank request){
        return new ResponseEntity<>(requestService.sendToBank(request), HttpStatus.OK);
    }
}
