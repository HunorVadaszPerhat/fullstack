package com.example.backend.service.humanresources.jobcandidate;

import com.example.backend.domain.model.humanresources.jobcandidate.JobCandidate;
import com.example.backend.dto.humanresources.jobcandidate.JobCandidateDto;
import com.example.backend.mapper.humanresources.jobcandidate.JobCandidateMapper;
import com.example.backend.repository.humanresources.jobcandidate.JobCandidateRepository;
import com.example.backend.util.response.PagedResponse;
import io.micrometer.core.annotation.Timed;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backend.constants.CacheNames.*;

@Service
@RequiredArgsConstructor
@Transactional
public class JobCandidateServiceImpl implements JobCandidateService {

    private final JobCandidateRepository repository;
    private final JobCandidateMapper mapper;

    @Override
    @Cacheable(value = JOB_CANDIDATES_GET_ALL, key = "'all'")
    @Timed(value = "jobCandidate.get-all", description = "Time taken to get all job candidates")
    public List<JobCandidateDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = SEARCH_JOB_CANDIDATES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "jobCandidate.get-paginated", description = "Time taken to get paginated job candidates")
    public PagedResponse<JobCandidateDto> getPaginated(Pageable pageable) {
        Page<JobCandidate> page = repository.findAll(pageable);
        List<JobCandidateDto> content = page.stream().map(mapper::toDto).toList();
        return new PagedResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    @Cacheable(value = JOB_CANDIDATES_GET_BY_ID, key = "#id")
    @Timed(value = "jobCandidate.get-by-id", description = "Time taken to get job candidate by ID")
    public JobCandidateDto getById(Integer id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobCandidate not found: " + id)));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = JOB_CANDIDATES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_JOB_CANDIDATES, allEntries = true)
    })
    @Timed(value = "jobCandidate.create", description = "Time taken to create job candidate")
    public JobCandidateDto create(JobCandidateDto dto) {
        JobCandidate saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = JOB_CANDIDATES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_JOB_CANDIDATES, allEntries = true),
            @CacheEvict(value = JOB_CANDIDATES_GET_BY_ID, key = "#id")
    })
    @Timed(value = "jobCandidate.update", description = "Time taken to update job candidate")
    public JobCandidateDto update(Integer id, JobCandidateDto dto) {
        JobCandidate entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobCandidate not found: " + id));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = JOB_CANDIDATES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_JOB_CANDIDATES, allEntries = true),
            @CacheEvict(value = JOB_CANDIDATES_GET_BY_ID, key = "#id")
    })
    @Timed(value = "jobCandidate.delete", description = "Time taken to delete job candidate")
    public void delete(Integer id) {
        JobCandidate entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobCandidate not found: " + id));
        repository.delete(entity);
    }
}
