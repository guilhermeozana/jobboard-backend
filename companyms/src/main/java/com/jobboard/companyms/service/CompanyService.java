package com.jobboard.companyms.service;

import com.jobboard.companyms.client.ReviewClient;
import com.jobboard.companyms.model.Company;
import com.jobboard.companyms.exception.CompanyNotFoundException;
import com.jobboard.companyms.repository.CompanyRepository;
import com.jobboard.shared.dto.*;
import com.jobboard.shared.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    private final ReviewClient reviewClient;

    //    private final RestTemplate restTemplate;

    public List<CompanyWithReviewsDTO> getAllCompanies() {
        List<Company> companyList = companyRepository.findAll();

        return companyList.stream().map(this::mapToCompanyWithReviewsDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company not found"));

        return GenericMapper.map(company, CompanyDTO.class);
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

    CompanyWithReviewsDTO mapToCompanyWithReviewsDTO(Company company) {
        CompanyDTO companyDTO = GenericMapper.map(company, CompanyDTO.class);
        List<ReviewDTO> reviewsList = reviewClient.getReviewsByCompanyId(company.getId());

        return CompanyWithReviewsDTOFactory.create(companyDTO, reviewsList);
    }

    public void updateCompanyRating(ReviewMessageDTO reviewMessage) {
        Company company = companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found"));

        company.setRating(reviewMessage.getCompanyRatingUpdated());

        companyRepository.save(company);
    }


//    List<ReviewDTO> getReviewsList(Long id) {
//
//        return restTemplate.exchange(
//                "http://REVIEW-SERVICE:8083/reviews?companyId=" + id,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<ReviewDTO>>() {}).getBody()
//    }

}
