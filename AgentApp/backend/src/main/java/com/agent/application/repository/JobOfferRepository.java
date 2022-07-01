package com.agent.application.repository;

import com.agent.application.model.JobOffer;
import com.agent.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

}
