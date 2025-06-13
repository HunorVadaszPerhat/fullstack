package com.example.backend.service.person.stateprovince;

import com.example.backend.dto.person.stateprovince.StateProvinceDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StateProvinceService {
    StateProvinceDto create(StateProvinceDto dto);
    StateProvinceDto getById(Integer id);
    List<StateProvinceDto> getAll();
    PagedResponse<StateProvinceDto> getPaginated(Pageable pageable);
    StateProvinceDto update(Integer id, StateProvinceDto dto);
    void delete(Integer id);
}

