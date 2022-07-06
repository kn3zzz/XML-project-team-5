package com.agent.application.service;

import com.agent.application.dto.CompanyRegistrationDTO;
import com.agent.application.model.Company;
import com.agent.application.repository.CompanyRepository;
import com.agent.application.mapper.CompanyRegistrationMapper;
import com.agent.application.model.User;
import com.agent.application.repository.UserRepository;
import com.agent.application.service.intereface.UserTypeService;
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

    @Autowired
    private UserTypeService userTypeService;

    @Override
    public boolean saveRegistrationCompany(CompanyRegistrationDTO company) {
        User user = this.userRepository.findByEmail(company.getOwnerEmail());
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
        List<Company> companies = companyRepository.findAll();
//        for(Company c : companyRepository.findAll()){
//            if(c.isActive()){
//                companys.add(c);
//            }
//        }
        return companies;
    }

    @Override
    public Company findCompanyById(Long id) {
        return this.companyRepository.findCompanyById(id);
    }

    @Override
    public boolean approveRequest(Long requestId) {
        Company request = this.companyRepository.findCompanyById(requestId);
        if(request != null){
            request.setActive(true);
            updateRole("ROLE_COMPANY_OWNER", request.getOwnerEmail());
            this.companyRepository.save(request);
            return true;
        }
        return false;
    }

    @Override
    public boolean rejectRequest(Long requestId) {
        Company request = this.companyRepository.findCompanyById(requestId);
        if(request != null){
            request.setRejected(true);
            this.companyRepository.save(request);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCompanyInfo(Company oldCompany) {
        Company c = companyRepository.findCompanyById(oldCompany.getId());

        if(c != null){
            c.setName(oldCompany.getName());
            c.setDescription(oldCompany.getDescription());
            c.setPhoneNumber(oldCompany.getPhoneNumber());
            companyRepository.save(c);
            return true;
        }

        return false;
    }

    public void updateRole(String role, String ownerEmail){
        User user = this.userRepository.findByEmail(ownerEmail);
        user.setUserType(this.userTypeService.findUserTypeByName(role));
        this.userRepository.save(user);
    }
}
