package com.agent.application.service;

import com.agent.application.dto.CompanyRegistrationDTO;
import com.agent.application.model.Company;
import com.agent.application.repository.CompanyRepository;
import com.agent.application.mapper.CompanyRegistrationMapper;
import com.agent.application.model.User;
import com.agent.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService implements com.agent.application.service.intereface.CompanyService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public boolean saveRegistrationCompany(CompanyRegistrationDTO company) {
        User user = this.userRepository.findByEmail(company.getUserEmail());
        if(user != null && user.getCompany() == null){
            Company savedCompany = companyRepository.save(new CompanyRegistrationMapper().mapCompanyDtoToCompany(company));
            user.setCompany(savedCompany);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<Company> findAll() {
        List<Company> companys = new ArrayList<>();
        for(Company c : companyRepository.findAll()){
            if(c.isActive()){
                companys.add(c);
            }
        }
        return companys;
    }

    @Override
    public boolean approveRequest(Long requestId) {
        return false;
    }

    @Override
    public boolean rejectRequest(Long requestId) {
        return false;
    }
}
