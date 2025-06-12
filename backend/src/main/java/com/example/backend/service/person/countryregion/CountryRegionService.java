package com.example.backend.service.person.countryregion;

import com.example.backend.dto.person.countryregion.CountryRegionDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CountryRegionService {
    CountryRegionDto create(CountryRegionDto dto);
    CountryRegionDto getById(String code);
    List<CountryRegionDto> getAll();
    PagedResponse<CountryRegionDto> getPaginated(Pageable pageable);
    CountryRegionDto update(String code, CountryRegionDto dto);
    void delete(String code);
}

