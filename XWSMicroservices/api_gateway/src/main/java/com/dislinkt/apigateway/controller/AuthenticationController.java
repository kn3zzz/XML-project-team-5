package com.dislinkt.apigateway.controller;

import com.dislinkt.apigateway.dto.LoginDTO;
import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.apigateway.dto.UserInfoChangeDTO;
import com.dislinkt.apigateway.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/addUser")
    public ResponseEntity<Boolean> saveUser(@RequestBody NewUserDTO user){
        return new ResponseEntity<Boolean>(authenticationService.registerUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login){
        return new ResponseEntity<>(authenticationService.login(login), HttpStatus.OK);
    }

    @PostMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String searchQuery){
        return new ResponseEntity<>(authenticationService.searchProfile(searchQuery), HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserInfoChangeDTO info){
        System.out.println(info);
        return new ResponseEntity<>(authenticationService.updateUser(info), HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id){
        System.out.println(id);
        return new ResponseEntity<>(authenticationService.getUser(id), HttpStatus.OK);
    }

}
