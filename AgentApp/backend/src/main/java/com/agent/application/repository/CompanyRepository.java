package com.agent.application.repository;

import com.agent.application.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findCompanyById(Long id);
}
