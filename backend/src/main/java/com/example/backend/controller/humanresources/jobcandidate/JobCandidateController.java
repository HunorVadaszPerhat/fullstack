package com.example.backend.controller.humanresources.jobcandidate;

import com.example.backend.dto.humanresources.jobcandidate.JobCandidateDto;
import com.example.backend.service.humanresources.jobcandidate.JobCandidateService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-candidates")
@RequiredArgsConstructor
@Tag(name = "Job Candidate", description = "Job Candidate management APIs")
public class JobCandidateController {

    private final JobCandidateService service;

    @Operation(summary = "Get all job candidates")
    @GetMapping
    public ResponseEntity<List<JobCandidateDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get paginated job candidates")
    @GetMapping("/paginated")
    public PagedResponse<JobCandidateDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "jobCandidateId,asc") String[] sort
    ) {
        String sortBy = sort[0];
        Sort.Direction direction = (sort.length > 1 && sort[1].equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return service.getPaginated(pageable);
    }

    @Operation(summary = "Get job candidate by ID")
    @GetMapping("/{id}")
    public ResponseEntity<JobCandidateDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Create job candidate")
    @PostMapping
    public ResponseEntity<JobCandidateDto> create(@RequestBody JobCandidateDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Update job candidate")
    @PutMapping("/{id}")
    public ResponseEntity<JobCandidateDto> update(@PathVariable Integer id, @RequestBody JobCandidateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete job candidate")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

