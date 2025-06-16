package com.example.backend.service.production.culture;

import com.example.backend.domain.model.production.culture.Culture;
import com.example.backend.dto.production.culture.CultureDto;
import com.example.backend.mapper.production.culture.CultureMapper;
import com.example.backend.repository.production.culture.CultureRepository;
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
public class CultureServiceImpl implements CultureService {

    private final CultureRepository repository;
    private final CultureMapper mapper;

    @Override
    @Timed(value = "culture.create")
    @Caching(evict = {
            @CacheEvict(value = CULTURES_GET_ALL, allEntries = true),
            @CacheEvict(value = CULTURES_GET_BY_ID, key = "#dto.cultureId")
    })
    public CultureDto create(CultureDto dto) {
        Culture entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = CULTURES_GET_BY_ID, key = "#id")
    @Timed(value = "culture.get-by-id")
    public CultureDto getById(String id) {
        Culture entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Culture not found: " + id));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = CULTURES_GET_ALL)
    @Timed(value = "culture.get-all")
    public List<CultureDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = SEARCH_CULTURES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "culture.get-paginated")
    public PagedResponse<CultureDto> getPaginated(Pageable pageable) {
        Page<Culture> page = repository.findAll(pageable);
        List<CultureDto> content = page.getContent().stream()
                .map(mapper::toDto)
                .toList();
        return new PagedResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    @Timed(value = "culture.update")
    @Caching(evict = {
            @CacheEvict(value = CULTURES_GET_ALL, allEntries = true),
            @CacheEvict(value = CULTURES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = SEARCH_CULTURES, allEntries = true)
    })
    public CultureDto update(String id, CultureDto dto) {
        Culture entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Culture not found: " + id));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Timed(value = "culture.delete")
    @Caching(evict = {
            @CacheEvict(value = CULTURES_GET_ALL, allEntries = true),
            @CacheEvict(value = CULTURES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = SEARCH_CULTURES, allEntries = true)
    })
    public void delete(String id) {
        Culture entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Culture not found: " + id));
        repository.delete(entity);
    }
}

