package com.agent.application.service.intereface;

import com.agent.application.model.Company;
import com.agent.application.dto.CompanyRegistrationDTO;

import java.util.List;

public interface CompanyService {
    boolean saveRegistrationCompany(CompanyRegistrationDTO company);

    List<Company> findAll();

    Company findCompanyById(Long id);

    boolean approveRequest(Long requestId);

    boolean rejectRequest(Long requestId);

    boolean updateCompanyInfo(Company OldCompany);
}
