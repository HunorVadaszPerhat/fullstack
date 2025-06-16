package com.example.backend.service.production.location;

import com.example.backend.dto.production.location.LocationDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    LocationDto create(LocationDto dto);
    LocationDto getById(Short id);
    List<LocationDto> getAll();
    PagedResponse<LocationDto> getPaginated(Pageable pageable);
    LocationDto update(Short id, LocationDto dto);
    void delete(Short id);
}

