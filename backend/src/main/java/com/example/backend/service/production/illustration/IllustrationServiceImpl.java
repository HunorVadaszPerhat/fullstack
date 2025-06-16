package com.example.backend.service.production.illustration;

import com.example.backend.domain.model.production.illustration.Illustration;
import com.example.backend.dto.production.illustration.IllustrationDto;
import com.example.backend.mapper.production.illustration.IllustrationMapper;
import com.example.backend.repository.production.illustration.IllustrationRepository;
import com.example.backend.util.response.PagedResponse;
import io.micrometer.core.annotation.Timed;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backend.constants.CacheNames.*;

@Service
@RequiredArgsConstructor
@Transactional
public class IllustrationServiceImpl implements IllustrationService {

    private final IllustrationRepository repository;
    private final IllustrationMapper mapper;

    @Override
    @Caching(evict = {
            @CacheEvict(value = DEPARTMENTS_GET_ALL, allEntries = true),
            @CacheEvict(value = DEPARTMENTS_GET_BY_ID, key = "#result.illustrationId", condition = "#result != null")
    })
    @Timed(value = "illustration.create", description = "Time taken to create illustration")
    public IllustrationDto create(IllustrationDto dto) {
        Illustration entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Cacheable(value = ILLUSTRATIONS_GET_BY_ID, key = "#id")
    @Timed(value = "illustration.get-by-id", description = "Time to retrieve by ID")
    public IllustrationDto getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Illustration not found: " + id));
    }

    @Override
    @Cacheable(value = ILLUSTRATIONS_GET_ALL)
    @Timed(value = "illustration.get-all", description = "Time to retrieve all")
    public List<IllustrationDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = SEARCH_ILLUSTRATIONS, key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    @Timed(value = "illustration.get-paginated", description = "Time to retrieve paginated illustrations")
    public PagedResponse<IllustrationDto> getPaginated(Pageable pageable) {
        Page<IllustrationDto> page = repository.findAll(pageable).map(mapper::toDto);
        return new PagedResponse<>(page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = ILLUSTRATIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = ILLUSTRATIONS_GET_BY_ID, key = "#id")
    })
    @Timed(value = "illustration.update", description = "Time taken to update illustration")
    public IllustrationDto update(Integer id, IllustrationDto dto) {
        Illustration entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Illustration not found: " + id));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = ILLUSTRATIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = ILLUSTRATIONS_GET_BY_ID, key = "#id")
    })
    @Timed(value = "illustration.delete", description = "Time taken to delete illustration")
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}

