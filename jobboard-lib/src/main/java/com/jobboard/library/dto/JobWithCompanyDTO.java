package com.jobboard.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobWithCompanyDTO {
    private JobDTO job;
    private CompanyDTO company;
}
