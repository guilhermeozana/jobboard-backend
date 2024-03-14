package com.jobboard.companyms.controller;

import com.jobboard.companyms.service.CompanyService;
import com.jobboard.library.dto.CompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies(){
        return new ResponseEntity<>(companyService.getAllCompanies(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id){
        CompanyDTO companyDTO = companyService.getCompanyById(id);

        return ResponseEntity.ok(companyDTO);
    }
    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody CompanyDTO companyDTO){
        companyService.createCompany(companyDTO);
        return new ResponseEntity<>("Company saved successfully",
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id,
                                                @RequestBody CompanyDTO companyDTO){
        companyService.updateCompany(companyDTO, id);
        return new ResponseEntity<>("Company updated successfully",
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        companyService.deleteCompanyById(id);

        return ResponseEntity.ok("Company successfully Deleted");
    }
}
