package com.jobboard.jobms.service;

import com.jobboard.jobms.model.Job;
import com.jobboard.jobms.exception.JobNotFoundException;
import com.jobboard.jobms.repository.JobRepository;
import com.jobboard.library.dto.CompanyDTO;
import com.jobboard.library.dto.JobDTO;
import com.jobboard.library.dto.JobWithCompanyDTO;
import com.jobboard.library.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {
    // private List<Job> jobs = new ArrayList<>();
    final JobRepository jobRepository;

    final RestTemplate restTemplate;

    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(job -> createJobWithCompanyDTO(GenericMapper.map(job, JobDTO.class), getCompanyById(job.getCompanyId())))
                .collect(Collectors.toList());
    }

    CompanyDTO getCompanyById(Long id) {
        return restTemplate.getForObject(
            "http://COMPANY-SERVICE:8081/companies/" + id,
            CompanyDTO.class);
    }

    public void createJob(Job job) {
        jobRepository.save(job);
    }

    public JobWithCompanyDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);

        return createJobWithCompanyDTO(GenericMapper.map(job, JobDTO.class), getCompanyById(job.getCompanyId()));
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

    JobWithCompanyDTO createJobWithCompanyDTO(JobDTO jobDTO, CompanyDTO companyDTO) {
        return JobWithCompanyDTO.builder()
                .company(companyDTO)
                .job(jobDTO)
                .build();
    }
}