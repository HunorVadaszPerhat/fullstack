package com.example.backend.service.humanresources.employeedepartmenthistory;

import com.example.backend.domain.model.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryId;
import com.example.backend.dto.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeDepartmentHistoryService {
    EmployeeDepartmentHistoryDto getById(EmployeeDepartmentHistoryId id);
    List<EmployeeDepartmentHistoryDto> getAll();
    PagedResponse<EmployeeDepartmentHistoryDto> getPaginated(Pageable pageable);
    EmployeeDepartmentHistoryDto create(EmployeeDepartmentHistoryDto dto);
    EmployeeDepartmentHistoryDto update(EmployeeDepartmentHistoryId id, EmployeeDepartmentHistoryDto dto);
    void delete(EmployeeDepartmentHistoryId id);
}
