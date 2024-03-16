package com.jobboard.jobms.controller;

import com.jobboard.jobms.service.JobService;
import com.jobboard.shared.dto.JobDTO;
import com.jobboard.shared.dto.JobWithCompanyReviewsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService; 

    @GetMapping
    public ResponseEntity<List<JobWithCompanyReviewsDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO job){
        Long jobId = jobService.createJob(job);

        return ResponseEntity.created(URI.create("jobs/" + jobId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompanyReviewsDTO> getJobById(@PathVariable Long id){
        JobWithCompanyReviewsDTO jobById = jobService.getJobById(id);

        return ResponseEntity.ok(jobById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        jobService.deleteJobById(id);

        return ResponseEntity.ok("Job deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id,
                                            @RequestBody JobDTO updatedJob){
        jobService.updateJob(id, updatedJob);

        return ResponseEntity.ok("Job updated successfully");
    }
}
