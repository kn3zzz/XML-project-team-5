package com.agent.application.service.impl;

import com.agent.application.dto.JobOfferDTO;
import com.agent.application.dto.JobOfferDislinktDTO;
import com.agent.application.mapper.JobOfferMapper;
import com.agent.application.model.Company;
import com.agent.application.model.JobOffer;
import com.agent.application.repository.CompanyRepository;
import com.agent.application.repository.JobOfferRepository;
import com.agent.application.service.intereface.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class JobOfferServiceImpl implements JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public boolean saveJobOffer(JobOfferDTO dto) {
        Company company = this.companyRepository.findCompanyById(dto.getCompanyId());
        if (company != null && company.isActive()) {
            JobOffer jobOffer = new JobOfferMapper().mapJobOfferDtoToJobOffer(dto);
            jobOffer.setCompany(company);
            this.jobOfferRepository.save(jobOffer);
            Boolean check = doesEmailExits(company.getOwnerEmail());
            if (check) {
                System.out.println(createPostWithObject(dto, company));
            } else
                System.out.println("FALSE");
            return true;
        }
        return false;
    }

    public Boolean createPostWithObject(JobOfferDTO dto, Company company) {
        String url = "http://localhost:8000/jobOffers/addJobOfferAgent";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("dislinktsecretAPIkey");
        // create a post object
        JobOfferDislinktDTO post = new JobOfferDislinktDTO (company.getName(),
                dto.getPosition().getName(),
                dto.getJobDescription(),
                dto.getDailyActivities(),
                "");

        // build the request
        HttpEntity<JobOfferDislinktDTO> entity = new HttpEntity<>(post, headers);

        // send POST request
        return restTemplate().postForObject(url, entity, Boolean.class);
    }

    public Boolean doesEmailExits(String email) {
        String url = "http://localhost:8000/users/findEmailAgent";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("dislinktsecretAPIkey");
        // create a post object

        // build the request
        HttpEntity<String> entity = new HttpEntity<>(email, headers);

        // send POST request
        return restTemplate().postForObject(url, entity, Boolean.class);
    }



    @Override
    public List<JobOfferDTO> getAll() {
        List<JobOfferDTO> dtos = new ArrayList<>();
        for (JobOffer j: this.jobOfferRepository.findAll()) {
            JobOfferDTO dto = new JobOfferMapper().mapJobOfferToJobOfferDto(j);
            dtos.add(dto);
        }
        return dtos;
    }
    @Bean
    private RestTemplate restTemplate () {
        return new RestTemplate();
    }

}
