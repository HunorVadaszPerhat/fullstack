package com.example.backend.service.production.illustration;

import com.example.backend.dto.production.illustration.IllustrationDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IllustrationService {
    IllustrationDto create(IllustrationDto dto);
    IllustrationDto getById(Integer id);
    List<IllustrationDto> getAll();
    PagedResponse<IllustrationDto> getPaginated(Pageable pageable);
    IllustrationDto update(Integer id, IllustrationDto dto);
    void delete(Integer id);
}

