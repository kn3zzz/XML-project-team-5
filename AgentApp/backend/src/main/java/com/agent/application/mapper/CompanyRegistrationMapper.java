package com.agent.application.mapper;

import com.agent.application.dto.CompanyRegistrationDTO;
import com.agent.application.model.Company;

public class CompanyRegistrationMapper {

    public Company mapCompanyDtoToCompany(CompanyRegistrationDTO dto){
        Company company = new Company();
        company.setActive(false);
        company.setDescription(dto.getDescription());
        company.setName(dto.getName());
        company.setPhoneNumber(dto.getPhoneNumber());
        return company;
    }
}
