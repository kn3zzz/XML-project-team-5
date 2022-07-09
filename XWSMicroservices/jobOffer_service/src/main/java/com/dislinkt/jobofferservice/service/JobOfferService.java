package com.dislinkt.jobofferservice.service;

import com.dislinkt.grpc.*;
import com.dislinkt.jobofferservice.model.JobOffer;
import com.dislinkt.jobofferservice.repository.JobOfferRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.NoSuchElementException;

@EnableMongoRepositories("com.dislinkt.jobofferservice.repository")
@GrpcService
public class JobOfferService extends JobOfferServiceGrpc.JobOfferServiceImplBase {

    @Autowired
    JobOfferRepository jobOfferRepository;
    @Override
    public void saveJobOffer(NewJobOffer request, StreamObserver<NewJobOfferResponse> responseObserver) {
        jobOfferRepository.save(new JobOffer(generateId(), request.getCompany(), request.getPosition(), request.getJobDescription(), request.getDailyActivities(), request.getPreconditions()));
        NewJobOfferResponse res = NewJobOfferResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void getJobOffers(SearchJobOffers request, StreamObserver<GetJobOffersResponse> responseObserver) {
            GetJobOffersResponse.Builder res = GetJobOffersResponse.newBuilder();
            for (JobOffer u : jobOfferRepository.findAll()) {
                res.addJobOffers(NewJobOffer.newBuilder()
                                .setCompany(u.getCompanyName())
                                .setPosition(u.getPosition())
                                .setJobDescription(u.getJobDescription())
                                .setDailyActivities(u.getDailyActivities())
                                .setPreconditions(u.getPreconditions())
                                .build());
                }
            responseObserver.onNext(res.build());
            responseObserver.onCompleted();
    }

    @Override
    public void searchJobOffers(SearchJobOffers request, StreamObserver<GetJobOffersResponse> responseObserver) {
        GetJobOffersResponse.Builder res = GetJobOffersResponse.newBuilder();
        String query = request.getQuery().toLowerCase();
        for (JobOffer u : jobOfferRepository.findAll()) {
            if (u.getCompanyName().toLowerCase().contains(query) ||
                    u.getPosition().toLowerCase().contains(query) ||
                    u.getJobDescription().toLowerCase().contains(query) ||
                    u.getDailyActivities().toLowerCase().contains(query) ||
                    u.getPreconditions().toLowerCase().contains(query)) {
                res.addJobOffers(NewJobOffer.newBuilder()
                        .setCompany(u.getCompanyName())
                        .setPosition(u.getPosition())
                        .setJobDescription(u.getJobDescription())
                        .setDailyActivities(u.getDailyActivities())
                        .setPreconditions(u.getPreconditions())
                        .build());
            }
        }
        responseObserver.onNext(res.build());
        responseObserver.onCompleted();
    }

    private long generateId() {
        for (long i = 1; i < 1000000000; i++) {
            try {
                jobOfferRepository.findById(i).get();
            } catch (NoSuchElementException e){
                return i;
            }
        }
        return 0;
    }
}
