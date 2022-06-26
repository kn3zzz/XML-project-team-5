package com.dislinkt.authenticationservice.controller;

import com.dislinkt.authenticationservice.dto.JwtAuthenticationRequest;
import com.dislinkt.authenticationservice.dto.UserTokenState;
import com.dislinkt.authenticationservice.model.User;
import com.dislinkt.authenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

}
