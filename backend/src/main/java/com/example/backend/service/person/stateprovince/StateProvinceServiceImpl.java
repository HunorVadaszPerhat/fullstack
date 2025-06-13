package com.example.backend.service.person.stateprovince;

import com.example.backend.domain.model.person.stateprovince.StateProvince;
import com.example.backend.domain.repository.person.stateprovince.StateProvinceRepository;
import com.example.backend.dto.person.stateprovince.StateProvinceDto;
import com.example.backend.mapper.person.stateprovince.EntityResolver;
import com.example.backend.mapper.person.stateprovince.StateProvinceMapper;
import com.example.backend.util.response.PagedResponse;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
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
import java.util.UUID;

import static com.example.backend.constants.CacheNames.*;

@Service
@RequiredArgsConstructor
@Transactional
public class StateProvinceServiceImpl implements StateProvinceService {

    private final StateProvinceRepository repository;
    private final StateProvinceMapper mapper;
    private final EntityResolver resolver;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = STATE_PROVINCES_GET_ALL, key = "'all'")
    @Timed(value = "stateProvince.get-all", description = "Time taken to get all state provinces (non-paginated)")
    public List<StateProvinceDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = STATE_PROVINCES_GET_BY_ID, key = "#id")
    @Timed(value = "stateProvince.get-by-id", description = "Time taken to get state province by ID")
    public StateProvinceDto getById(Integer id) {
        StateProvince entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StateProvince not found with ID: " + id));
        return mapper.toDto(entity);
    }

    @Override
    @Cacheable(value = SEARCH_STATE_PROVINCES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "stateProvince.get-paginated", description = "Time taken to get paginated state provinces")
    public PagedResponse<StateProvinceDto> getPaginated(Pageable pageable) {
        Page<StateProvince> page = repository.findAll(pageable);
        List<StateProvinceDto> content = page.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();
        return new PagedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = STATE_PROVINCES_GET_BY_ID, key = "#result.stateProvinceId", condition = "#result != null"),
            @CacheEvict(value = STATE_PROVINCES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_STATE_PROVINCES, allEntries = true)
    })
    @Timed(value = "stateProvince.create", description = "Time taken to create state province")
    public StateProvinceDto create(StateProvinceDto dto) {
        StateProvince entity = mapper.toEntity(dto, resolver);
        entity.setRowguid(UUID.randomUUID());
        StateProvince saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = STATE_PROVINCES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = STATE_PROVINCES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_STATE_PROVINCES, allEntries = true)
    })
    @Timed(value = "stateProvince.update", description = "Time taken to update state province")
    public StateProvinceDto update(Integer id, StateProvinceDto dto) {
        StateProvince entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StateProvince not found with ID: " + id));

        mapper.updateEntityFromDto(dto, entity, resolver);
        StateProvince updated = repository.save(entity);
        return mapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = STATE_PROVINCES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = STATE_PROVINCES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_STATE_PROVINCES, allEntries = true)
    })
    @Timed(value = "stateProvince.delete", description = "Time taken to delete state province")
    public void delete(Integer id) {
        StateProvince entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StateProvince not found with ID: " + id));
        repository.delete(entity);
    }
}

