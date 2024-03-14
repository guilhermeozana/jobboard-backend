package com.jobboard.jobms.repository;

import com.jobboard.jobms.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
