package com.dislinkt.authenticationservice.controller;

import com.dislinkt.authenticationservice.dto.JwtAuthenticationRequest;
import com.dislinkt.authenticationservice.dto.UserTokenState;
import com.dislinkt.authenticationservice.model.User;
import com.dislinkt.authenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public void saveUser(@RequestBody User user){
        userService.save(user);
    }

    @GetMapping("/getUsers")
    public List<User> getUsers(){
        return userService.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {
        UserTokenState state = null;
        User user = userService.getByUsername(authenticationRequest.getUsername());
        try {
            if (user.getUsername().equalsIgnoreCase(authenticationRequest.getUsername()) && user.getPassword().equals(authenticationRequest.getPassword())) {
                state = new UserTokenState("uiuwehfuhewuifbwnbfiew", 8640000, user.getRole(), user.isEnabled(), user.isNotificationsOn(), user.getUsername(), user.getId());
                return new ResponseEntity<UserTokenState>(state, HttpStatus.OK);
            } else {
                return new ResponseEntity<UserTokenState>(state, HttpStatus.BAD_REQUEST);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            return new ResponseEntity<UserTokenState>(state, HttpStatus.NO_CONTENT);
        }
    }
}
