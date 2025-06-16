package com.example.backend.service.humanresources.jobcandidate;

import com.example.backend.dto.humanresources.jobcandidate.JobCandidateDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobCandidateService {
    List<JobCandidateDto> getAll();
    PagedResponse<JobCandidateDto> getPaginated(Pageable pageable);
    JobCandidateDto getById(Integer id);
    JobCandidateDto create(JobCandidateDto dto);
    JobCandidateDto update(Integer id, JobCandidateDto dto);
    void delete(Integer id);
}
