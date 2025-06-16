package com.example.backend.service.production.location;

import com.example.backend.domain.model.production.location.Location;
import com.example.backend.dto.production.location.LocationDto;
import com.example.backend.mapper.production.location.LocationMapper;
import com.example.backend.repository.production.location.LocationRepository;
import com.example.backend.util.response.PagedResponse;
import io.micrometer.core.annotation.Timed;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backend.constants.CacheNames.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;
    private final LocationMapper mapper;

    @Override
    public LocationDto create(LocationDto dto) {
        Location saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    @Cacheable(value = LOCATIONS_GET_BY_ID, key = "#id")
    @Timed(value = "location.get-by-id", description = "Get location by id")
    public LocationDto getById(Short id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found")));
    }

    @Override
    @Cacheable(value = LOCATIONS_GET_ALL)
    @Timed(value = "location.get-all", description = "Time to retrieve all")
    public List<LocationDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = SEARCH_LOCATIONS, key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    @Timed(value = "location.get-paginated", description = "Time to retrieve paginated locations")
    public PagedResponse<LocationDto> getPaginated(Pageable pageable) {
        Page<Location> page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream().map(mapper::toDto).toList(),
                page.getNumber(), page.getSize(), page.getTotalElements(),
                page.getTotalPages(), page.isLast());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = LOCATIONS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = LOCATIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_LOCATIONS, allEntries = true)
    })
    @Timed(value = "location.update", description = "Time taken to update location")
    public LocationDto update(Short id, LocationDto dto) {
        Location entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = LOCATIONS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = LOCATIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_LOCATIONS, allEntries = true)
    })
    @Timed(value = "location.delete", description = "Time taken to delete location")
    public void delete(Short id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Location not found");
        }
        repository.deleteById(id);
    }
}

