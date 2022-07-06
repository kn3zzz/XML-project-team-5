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

    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return new ResponseEntity<>("HI", HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<Boolean> saveUser(@RequestBody NewUserDTO user){
        return new ResponseEntity<Boolean>(authenticationService.registerUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login){
        return new ResponseEntity<>(authenticationService.login(login), HttpStatus.OK);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String searchQuery){
        return new ResponseEntity<>(authenticationService.searchProfile(searchQuery), HttpStatus.OK);
    }

    @GetMapping("/profiles")
    public ResponseEntity<?> profiles(){
        return new ResponseEntity<>(authenticationService.searchProfile(" "), HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserInfoChangeDTO info){
        return new ResponseEntity<>(authenticationService.updateUser(info), HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id){
        return new ResponseEntity<>(authenticationService.getUser(id), HttpStatus.OK);
    }

}
