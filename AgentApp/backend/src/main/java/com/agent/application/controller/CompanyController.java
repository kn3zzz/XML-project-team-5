package com.agent.application.controller;

import com.agent.application.dto.CompanyRegistrationDTO;
import com.agent.application.service.intereface.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveRegistrationCompany(@RequestBody CompanyRegistrationDTO dto){
        boolean saved = this.companyService.saveRegistrationCompany(dto);
        if(saved){
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    };

    @GetMapping
    public ResponseEntity<?> getAll( @RequestHeader("Authorization") String jwtToken) {
        return new ResponseEntity(this.companyService.findAll(), HttpStatus.OK);
    }
}
