package com.example.backend.service.humanresources.employeepayhistory;

import com.example.backend.domain.model.humanresources.employeepayhistory.EmployeePayHistoryId;
import com.example.backend.dto.humanresources.employeepayhistory.EmployeePayHistoryDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeePayHistoryService {
    List<EmployeePayHistoryDto> getAll();
    PagedResponse<EmployeePayHistoryDto> getPaginated(Pageable pageable);
    EmployeePayHistoryDto getById(EmployeePayHistoryId id);
    EmployeePayHistoryDto create(EmployeePayHistoryDto dto);
    EmployeePayHistoryDto update(EmployeePayHistoryId id, EmployeePayHistoryDto dto);
    void delete(EmployeePayHistoryId id);
}

