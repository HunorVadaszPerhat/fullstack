package com.example.backend.mapper.production.document;

import com.example.backend.domain.model.humanresources.employee.Employee;
import com.example.backend.repository.humanresources.employee.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentResolver {

    private final EmployeeRepository employeeRepository;

    public Employee resolveOwner(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));
    }
}
