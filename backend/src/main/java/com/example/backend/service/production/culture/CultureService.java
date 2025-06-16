package com.example.backend.service.production.culture;

import com.example.backend.dto.production.culture.CultureDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CultureService {
    CultureDto create(CultureDto dto);
    CultureDto getById(String id);
    List<CultureDto> getAll();
    PagedResponse<CultureDto> getPaginated(Pageable pageable);
    CultureDto update(String id, CultureDto dto);
    void delete(String id);
}

