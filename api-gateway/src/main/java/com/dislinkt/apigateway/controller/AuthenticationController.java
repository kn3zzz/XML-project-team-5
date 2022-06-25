package com.dislinkt.apigateway.controller;

import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.apigateway.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/addUser")
    public ResponseEntity<?> saveUser(@RequestBody NewUserDTO user){
        return new ResponseEntity<>(authenticationService.registerUser(user), HttpStatus.CREATED);

    }
}
