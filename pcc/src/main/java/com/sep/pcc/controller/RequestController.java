package com.sep.pcc.controller;

import com.sep.pcc.model.RequestFromBank;
import com.sep.pcc.model.RequestTest;
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
    public ResponseEntity<ResponseFromBank> request(@RequestBody RequestFromBank requestFromBank){
        return new ResponseEntity<>(requestService.sendToBank(requestFromBank), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> test(){
        return new ResponseEntity<>(requestService.test(), HttpStatus.OK);
    }

}
