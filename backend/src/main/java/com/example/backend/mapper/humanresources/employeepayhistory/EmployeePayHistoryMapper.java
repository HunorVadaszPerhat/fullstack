package com.example.backend.mapper.humanresources.employeepayhistory;

import com.example.backend.domain.model.humanresources.employee.Employee;
import com.example.backend.domain.model.humanresources.employeepayhistory.EmployeePayHistory;
import com.example.backend.domain.model.humanresources.employeepayhistory.EmployeePayHistoryId;
import com.example.backend.dto.humanresources.employeepayhistory.EmployeePayHistoryDto;
import com.example.backend.repository.humanresources.employee.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeePayHistoryMapper {

    private final EmployeeRepository employeeRepository;

    public EmployeePayHistoryDto toDto(EmployeePayHistory entity) {
        return EmployeePayHistoryDto.builder()
                .businessEntityId(entity.getId().getBusinessEntityId())
                .rateChangeDate(entity.getId().getRateChangeDate())
                .rate(entity.getRate())
                .payFrequency(entity.getPayFrequency())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public EmployeePayHistory toEntity(EmployeePayHistoryDto dto) {
        Employee employee = employeeRepository.findById(dto.getBusinessEntityId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + dto.getBusinessEntityId()));

        return EmployeePayHistory.builder()
                .id(new EmployeePayHistoryId(dto.getBusinessEntityId(), dto.getRateChangeDate()))
                .employee(employee)
                .rate(dto.getRate())
                .payFrequency(dto.getPayFrequency())
                .build();
    }

    public void updateEntityFromDto(EmployeePayHistoryDto dto, EmployeePayHistory entity) {
        entity.setRate(dto.getRate());
        entity.setPayFrequency(dto.getPayFrequency());
        // Do not change ID (composite key)
    }
}

