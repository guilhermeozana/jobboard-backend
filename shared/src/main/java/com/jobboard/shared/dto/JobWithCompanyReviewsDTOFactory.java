package com.jobboard.shared.dto;

import org.springframework.beans.BeanUtils;

public class JobWithCompanyReviewsDTOFactory {

    public static JobWithCompanyReviewsDTO create(JobDTO jobDTO, CompanyWithReviewsDTO companyWithReviewsDTO) {
        JobWithCompanyReviewsDTO jobWithCompanyDTO = new JobWithCompanyReviewsDTO();

        //Copy common properties
        BeanUtils.copyProperties(jobDTO, jobWithCompanyDTO);

        jobWithCompanyDTO.setCompanyWithReviews(companyWithReviewsDTO);

        return jobWithCompanyDTO;
    }
}
