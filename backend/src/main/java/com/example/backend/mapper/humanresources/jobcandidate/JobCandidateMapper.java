package com.example.backend.mapper.humanresources.jobcandidate;

import com.example.backend.domain.model.humanresources.employee.Employee;
import com.example.backend.domain.model.humanresources.jobcandidate.JobCandidate;
import com.example.backend.dto.humanresources.jobcandidate.JobCandidateDto;
import com.example.backend.repository.humanresources.employee.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobCandidateMapper {

    private final EmployeeRepository employeeRepository;

    public JobCandidateDto toDto(JobCandidate entity) {
        return JobCandidateDto.builder()
                .jobCandidateId(entity.getJobCandidateId())
                .employeeId(entity.getEmployee() != null ? entity.getEmployee().getBusinessEntityId() : null)
                .resume(entity.getResume())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public JobCandidate toEntity(JobCandidateDto dto) {
        Employee employee = null;
        if (dto.getEmployeeId() != null) {
            employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + dto.getEmployeeId()));
        }

        return JobCandidate.builder()
                .jobCandidateId(dto.getJobCandidateId())
                .employee(employee)
                .resume(dto.getResume())
                .build();
    }

    public void updateEntityFromDto(JobCandidateDto dto, JobCandidate entity) {
        if (dto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + dto.getEmployeeId()));
            entity.setEmployee(employee);
        }
        entity.setResume(dto.getResume());
    }
}
