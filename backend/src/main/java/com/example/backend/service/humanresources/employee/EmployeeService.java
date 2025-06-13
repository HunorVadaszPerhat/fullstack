package com.example.backend.service.humanresources.employee;

import com.example.backend.dto.humanresources.employee.EmployeeDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    EmployeeDto create(EmployeeDto dto);
    EmployeeDto getById(Integer id);
    List<EmployeeDto> getAll();
    PagedResponse<EmployeeDto> getPaginated(Pageable pageable);
    EmployeeDto update(Integer id, EmployeeDto dto);
    void delete(Integer id);
}
