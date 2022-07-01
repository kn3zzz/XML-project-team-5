package com.agent.application.mapper;

import com.agent.application.dto.JobOfferDTO;
import com.agent.application.model.JobOffer;
import com.agent.application.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class JobOfferMapper {

    public JobOfferDTO mapJobOfferToJobOfferDto(JobOffer jobOffer) {
        JobOfferDTO dto = new JobOfferDTO();
        dto.setId(jobOffer.getId());
        dto.setPosition(jobOffer.getPosition());
        dto.setJobDescription(jobOffer.getJobDescription());
        dto.setDailyActivities(jobOffer.getDailyActivities());
        dto.setCompanyId(jobOffer.getCompany().getId());
        return dto;
    }

    public JobOffer mapJobOfferDtoToJobOffer(JobOfferDTO dto) {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(dto.getId());
        jobOffer.setPosition(dto.getPosition());
        jobOffer.setJobDescription(dto.getJobDescription());
        jobOffer.setDailyActivities(dto.getDailyActivities());
        return jobOffer;
    }
}
