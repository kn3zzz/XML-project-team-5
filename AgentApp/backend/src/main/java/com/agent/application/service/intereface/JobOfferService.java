package com.agent.application.service.intereface;

import com.agent.application.dto.CompanyRegistrationDTO;
import com.agent.application.dto.JobOfferDTO;
import com.agent.application.model.Company;
import com.agent.application.model.JobOffer;

import java.util.List;

public interface JobOfferService {
    boolean saveJobOffer(JobOfferDTO jobOffer);

    List<JobOfferDTO> getAll();
}
