package com.dislinkt.apigateway.controller;

import com.dislinkt.apigateway.dto.JobOfferDTO;
import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.apigateway.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobOffers")
public class JobOfferController {

    @Autowired
    JobOfferService jobOfferService;

    @PostMapping("/addJobOffer")
    public ResponseEntity<Boolean> addJobOffer(@RequestBody JobOfferDTO offerDTO){
        return new ResponseEntity<Boolean>(jobOfferService.addJobOffer(offerDTO), HttpStatus.CREATED);
    }

    @PostMapping("/addJobOfferAgent")
    public ResponseEntity<Boolean> addJobOfferAgent(@RequestBody JobOfferDTO offerDTO, @RequestHeader("Authorization") String token){
        if (token.split(" ")[1].equals("dislinktsecretAPIkey"))
            return new ResponseEntity<Boolean>(jobOfferService.addJobOffer(offerDTO), HttpStatus.CREATED);
        else
            return new ResponseEntity<Boolean>(false, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String searchQuery){
        return new ResponseEntity<>(jobOfferService.searchJobOffers(searchQuery), HttpStatus.OK);
    }

    @GetMapping("/getAllOffers")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(jobOfferService.getAllJobOffers(), HttpStatus.OK);
    }


}
