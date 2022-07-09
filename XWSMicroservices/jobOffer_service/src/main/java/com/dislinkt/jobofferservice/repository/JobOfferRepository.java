package com.dislinkt.jobofferservice.repository;

import com.dislinkt.jobofferservice.model.JobOffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends MongoRepository<JobOffer, Long> {
}
