package com.jobboard.companyms.client;

import com.jobboard.shared.dto.ReviewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEW-SERVICE", url = "${review-service-url}")
public interface ReviewClient {

    @GetMapping("/reviews")
    List<ReviewDTO> getReviewsByCompanyId(@RequestParam Long companyId);

    @GetMapping("/getAverageRatingByCompany")
    Double getAverageRatingByCompany(@RequestParam Long companyId);
}
