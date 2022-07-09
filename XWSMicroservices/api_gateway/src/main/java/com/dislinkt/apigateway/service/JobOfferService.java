package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.JobOfferDTO;
import com.dislinkt.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobOfferService {

    @GrpcClient("job-offer-grpc-service")
    JobOfferServiceGrpc.JobOfferServiceBlockingStub jobOfferStub;

    public List<JobOfferDTO> getAllJobOffers() {
        SearchJobOffers req = SearchJobOffers.newBuilder().setQuery("").build();
        GetJobOffersResponse res = jobOfferStub.getJobOffers(req);
        return convertToJobOfferDTO(res);
    }

    private List<JobOfferDTO> convertToJobOfferDTO(GetJobOffersResponse res) {
        List<JobOfferDTO> offers = new ArrayList<>();
        for (NewJobOffer o : res.getJobOffersList())
            offers.add(new JobOfferDTO(o.getCompany(), o.getPosition(), o.getJobDescription(), o.getDailyActivities(), o.getPreconditions()));
        return offers;
    }

    public List<JobOfferDTO> searchJobOffers(String searchQuery) {
        SearchJobOffers req = SearchJobOffers.newBuilder().setQuery(searchQuery).build();
        GetJobOffersResponse res = jobOfferStub.searchJobOffers(req);
        return convertToJobOfferDTO(res);
    }

    public Boolean addJobOffer(JobOfferDTO offerDTO) {
        NewJobOffer req = NewJobOffer.newBuilder()
                .setCompany(offerDTO.company)
                .setPosition(offerDTO.position)
                .setJobDescription(offerDTO.jobDescription)
                .setDailyActivities(offerDTO.dailyActivities)
                .setPreconditions(offerDTO.preconditions)
                .build();
        NewJobOfferResponse res = jobOfferStub.saveJobOffer(req);
        if (res.getSuccess())
            return true;
        return false;
    }
}
