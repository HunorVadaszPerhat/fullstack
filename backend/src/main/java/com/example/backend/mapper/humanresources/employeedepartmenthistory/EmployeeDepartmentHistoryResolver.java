package com.example.backend.mapper.humanresources.employeedepartmenthistory;

import com.example.backend.domain.model.humanresources.department.Department;
import com.example.backend.domain.model.humanresources.employee.Employee;
import com.example.backend.domain.model.humanresources.shift.Shift;
import com.example.backend.repository.humanresources.department.DepartmentRepository;
import com.example.backend.repository.humanresources.employee.EmployeeRepository;
import com.example.backend.repository.humanresources.shift.ShiftRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeDepartmentHistoryResolver {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ShiftRepository shiftRepository;

    public Employee resolveEmployee(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));
    }

    public Department resolveDepartment(Short id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + id));
    }

    public Shift resolveShift(Byte id) {
        return shiftRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shift not found: " + id));
    }
}
