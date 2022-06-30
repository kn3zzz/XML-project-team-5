package com.agent.application.controller;

import com.agent.application.dto.ChangePasswordDTO;
import com.agent.application.service.intereface.UserService;
import com.agent.application.dto.SignUpUserDTO;
import com.agent.application.enums.UserType;
import com.agent.application.model.User;
import com.agent.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @GetMapping(value = "/getById/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id) {
        User user = userService.findUserById(id);
        if(user == null) {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('addAdmin')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/addAdmin")
    public ResponseEntity<?> addAdmin(@RequestBody SignUpUserDTO dto) throws Exception {
        User user = userService.registerAdmin(dto);
        return ResponseEntity.ok(user.getId());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{username}/company")
   // @PreAuthorize("hasAuthority('getCompanyByOwnerUsername')")
    public ResponseEntity<?> getCompanyByOwnerUsername(@PathVariable String username){
        return new ResponseEntity<>(userService.getCompanyByOwnerUsername(username), HttpStatus.OK);
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/getByEmail/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/findAllClients")
    public ResponseEntity<List<User>> findAllClients() {
        List<User> users = new ArrayList<>();
        for (User u: userService.findAll()) {
            if(u.getUserType().equals(UserType.ROLE_USER)){
                users.add(u);
            }
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping(value = "/changePassword",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO dto, Principal user) throws Exception {
        try {
            userService.changePassword(dto, user.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
