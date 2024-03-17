package com.jobboard.jobms.service;

import com.jobboard.jobms.client.CompanyClient;
import com.jobboard.jobms.client.ReviewClient;
import com.jobboard.jobms.exception.JobNotFoundException;
import com.jobboard.jobms.model.Job;
import com.jobboard.jobms.repository.JobRepository;
import com.jobboard.shared.dto.*;
import com.jobboard.shared.mapper.GenericMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {
    // private List<Job> jobs = new ArrayList<>();

    private final JobRepository jobRepository;

    private final CompanyClient companyClient;

    private final ReviewClient reviewClient;

    //    private final RestTemplate restTemplate;

    private static final List<JobWithCompanyReviewsDTO> JOBS_CACHE = new ArrayList<>();

    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    //@Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    //@RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobWithCompanyReviewsDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();

        List<JobWithCompanyReviewsDTO> listJobWithCompanyDTO = jobs.stream()
                .map(this::mapToJobWithCompanyReviewsDTO)
                .collect(Collectors.toList());
        
        JOBS_CACHE.addAll(listJobWithCompanyDTO);

        return listJobWithCompanyDTO;
        
    }

    private List<JobWithCompanyReviewsDTO> companyBreakerFallback(Throwable e) {
        if(JOBS_CACHE.isEmpty()) {
            List<Job> listJobs = jobRepository.findAll();

            //It returns the Job without the company while the company service is down increasing the resilience
            return listJobs
                        .stream()
                        .map(job -> JobWithCompanyReviewsDTOFactory
                                .create(GenericMapper.map(job, JobDTO.class), null)
                        )
                        .collect(Collectors.toList());
        }

        return JOBS_CACHE;
    }

    public Long createJob(JobDTO jobDTO) {
        Job job = jobRepository.save(GenericMapper.map(jobDTO, Job.class));
        
        return job.getId();
    }

    public JobWithCompanyReviewsDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job not found"));

        return mapToJobWithCompanyReviewsDTO(job);
    }

    public void deleteJobById(Long id) {
        jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job not found"));

        jobRepository.deleteById(id);
    }


    public void updateJob(Long id, JobDTO updatedJob) {
        Job jobSaved = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job not found"));

        BeanUtils.copyProperties(updatedJob, jobSaved);

        jobRepository.save(jobSaved);
    }

    private JobWithCompanyReviewsDTO mapToJobWithCompanyReviewsDTO(Job job) {
        CompanyDTO companyDTO = companyClient.getCompany(job.getCompanyId());
        List<ReviewDTO> reviewsList = reviewClient.getReviewsByCompanyId(job.getCompanyId());

        CompanyWithReviewsDTO companyWithReviewsDTO = CompanyWithReviewsDTOFactory.create(companyDTO, reviewsList);

        return JobWithCompanyReviewsDTOFactory.create(GenericMapper.map(job, JobDTO.class), companyWithReviewsDTO);
    }

//    CompanyDTO getCompanyById(Long id) {
//
//         return restTemplate.getForObject(
//                "http://COMPANY-SERVICE:8081/companies/" + id,
//                CompanyDTO.class);
//    }

//    List<ReviewDTO> getReviewsList(Long id) {
//        return reviewClient.getReviewsByCompanyId(id);
//
//       return restTemplate.exchange(
//                "http://REVIEW-SERVICE:8083/reviews?companyId=" + id,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<ReviewDTO>>() {}).getBody();
//    }
}