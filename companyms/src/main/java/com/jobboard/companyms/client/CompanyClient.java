package com.jobboard.companyms.client;

import com.jobboard.shared.dto.CompanyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COMPANY-SERVICE", url = "${company-service-url}")
public interface CompanyClient {

    @GetMapping("/companies/{id}")
    CompanyDTO getCompany(@PathVariable("id") Long id);
}
