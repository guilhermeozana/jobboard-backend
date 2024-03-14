package com.jobboard.companyms.repository;

import com.jobboard.companyms.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
