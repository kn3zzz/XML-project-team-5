package com.agent.application.controller;

import com.agent.application.dto.CompanyRegistrationDTO;
import com.agent.application.dto.JobOfferDTO;
import com.agent.application.model.Company;
import com.agent.application.service.intereface.CompanyService;
import com.agent.application.service.intereface.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobOfferService jobOfferService;

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

    @PutMapping (value = "/updateCompanyInfo")
    public ResponseEntity<?> updateCompanyInfo(@RequestBody Company oldCompany) {
        boolean updated = this.companyService.updateCompanyInfo(oldCompany);
        if (updated){return new ResponseEntity(HttpStatus.OK);}
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long id) {
        return new ResponseEntity(this.companyService.findCompanyById(id), HttpStatus.OK);
    }

    @PostMapping ( value = "/{companyId}/job_offer")
    public ResponseEntity<?> saveJobOffer(@Valid @RequestBody JobOfferDTO dto, @PathVariable Long companyId) {
        dto.setCompanyId(companyId);
        boolean saved = this.jobOfferService.saveJobOffer(dto);
        if (saved != false){
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping ( value = "/job_offer")
    public ResponseEntity<?> getAllJobOffers() {
        return new ResponseEntity(this.jobOfferService.getAll(), HttpStatus.OK);
    }
}
