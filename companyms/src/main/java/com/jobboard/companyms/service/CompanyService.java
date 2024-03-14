package com.jobboard.companyms.service;

import com.jobboard.companyms.model.Company;
import com.jobboard.companyms.exception.CompanyNotFoundException;
import com.jobboard.companyms.repository.CompanyRepository;
import com.jobboard.library.dto.CompanyDTO;
import com.jobboard.library.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public List<CompanyDTO> getAllCompanies() {
        List<Company> companyList = companyRepository.findAll();

        return companyList.stream().map(company -> GenericMapper.map(company, CompanyDTO.class))
                .collect(Collectors.toList());
    }

    public void updateCompany(CompanyDTO company, Long id) {
        Company companyToUpdate = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company not found"));

        BeanUtils.copyProperties(company, companyToUpdate);

        companyRepository.save(companyToUpdate);
    }

    public void createCompany(CompanyDTO companyDTO) {
        Company company = GenericMapper.map(companyDTO, Company.class);

        companyRepository.save(company);
    }

    public void deleteCompanyById(Long id) {
        companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company not found"));

        companyRepository.deleteById(id);
    }

    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company not found"));

        return GenericMapper.map(company, CompanyDTO.class);
    }

}
