package com.jobboard.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyWithReviewsDTO {
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private List<ReviewDTO> reviewsList;
}
