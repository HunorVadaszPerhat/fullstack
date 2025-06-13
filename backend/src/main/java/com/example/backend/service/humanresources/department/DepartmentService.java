package com.example.backend.service.humanresources.department;


import com.example.backend.dto.humanresources.department.DepartmentDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {
    DepartmentDto create(DepartmentDto dto);
    DepartmentDto getById(Short id);
    List<DepartmentDto> getAll();
    PagedResponse<DepartmentDto> getPaginated(Pageable pageable);
    DepartmentDto update(Short id, DepartmentDto dto);
    void delete(Short id);
}