package com.jobboard.shared.dto;


import org.springframework.beans.BeanUtils;

import java.util.List;

public class CompanyWithReviewsDTOFactory {

    public static CompanyWithReviewsDTO create(CompanyDTO companyDTO, List<ReviewDTO> reviewDTOList) {
        CompanyWithReviewsDTO companyWithReviewsDTO = new CompanyWithReviewsDTO();

        //Copy properties in common
        BeanUtils.copyProperties(companyDTO, companyWithReviewsDTO);

        companyWithReviewsDTO.setReviewsList(reviewDTOList);

        return companyWithReviewsDTO;
    }
}
