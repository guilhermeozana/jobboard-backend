package com.jobboard.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMessageDTO {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private Long companyId;
    private Double companyRatingUpdated;

}
