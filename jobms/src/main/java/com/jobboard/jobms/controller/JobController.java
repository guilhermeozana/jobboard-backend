package com.jobboard.jobms.controller;

import com.jobboard.jobms.model.Job;
import com.jobboard.jobms.service.JobService;
import com.jobboard.library.dto.JobDTO;
import com.jobboard.library.dto.JobWithCompanyDTO;
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
    public ResponseEntity<List<JobWithCompanyDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);

        return ResponseEntity.created(URI.create("/{id}")).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompanyDTO> getJobById(@PathVariable Long id){
        JobWithCompanyDTO jobWithCompanyDTO = jobService.getJobById(id);

        return ResponseEntity.ok(jobWithCompanyDTO);
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
