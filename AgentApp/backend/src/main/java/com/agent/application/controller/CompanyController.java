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
    public ResponseEntity<?> getAll() {
        return new ResponseEntity(this.companyService.findAll(), HttpStatus.OK);
    }

    @PutMapping (value = "/approve_request/{requestId}")
    public ResponseEntity<?> approveRequest(@PathVariable Long requestId) {
        boolean approved = this.companyService.approveRequest(requestId);
        if (approved){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping (value = "/reject_request/{requestId}")
    public ResponseEntity<?> rejectRequest(@PathVariable Long requestId) {
        boolean rejected = this.companyService.rejectRequest(requestId);
        if (rejected){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
