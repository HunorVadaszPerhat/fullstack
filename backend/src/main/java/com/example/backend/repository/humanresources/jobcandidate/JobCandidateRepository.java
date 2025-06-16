package com.example.backend.repository.humanresources.jobcandidate;

import com.example.backend.domain.model.humanresources.jobcandidate.JobCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCandidateRepository extends JpaRepository<JobCandidate, Integer> {
}