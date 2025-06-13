package com.example.backend.service.person.countryregion;

import com.example.backend.domain.model.person.countryregion.CountryRegion;
import com.example.backend.repository.person.countryregion.CountryRegionRepository;
import com.example.backend.dto.person.countryregion.CountryRegionDto;
import com.example.backend.mapper.person.countryregion.CountryRegionMapper;
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

import static com.example.backend.constants.CacheNames.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CountryRegionServiceImpl implements CountryRegionService {

    private final CountryRegionRepository countryRegionRepository;
    private final CountryRegionMapper countryRegionMapper;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = COUNTRY_REGIONS_GET_ALL, key = "'all'")
    @Timed(value = "countryRegion.get-all", description = "Time taken to get all country regions (non-paginated)")
    public List<CountryRegionDto> getAll() {
        return countryRegionRepository.findAll()
                .stream()
                .map(countryRegionMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = COUNTRY_REGIONS_GET_BY_ID, key = "#code")
    @Timed(value = "countryRegion.get-by-id", description = "Time taken to get country region by code")
    public CountryRegionDto getById(String code) {
        CountryRegion countryRegion = countryRegionRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("CountryRegion not found with code: " + code));
        return countryRegionMapper.toDto(countryRegion);
    }

    @Override
    @Cacheable(value = SEARCH_COUNTRY_REGIONS, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "countryRegion.get-paginated", description = "Time taken to get paginated country regions")
    public PagedResponse<CountryRegionDto> getPaginated(Pageable pageable) {
        Page<CountryRegion> page = countryRegionRepository.findAll(pageable);
        List<CountryRegionDto> content = page.getContent()
                .stream()
                .map(countryRegionMapper::toDto)
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
            @CacheEvict(value = COUNTRY_REGIONS_GET_BY_ID, key = "#result.countryRegionCode", condition = "#result != null"),
            @CacheEvict(value = COUNTRY_REGIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_COUNTRY_REGIONS, allEntries = true)
    })
    @Timed(value = "countryRegion.create", description = "Time taken to create country region")
    public CountryRegionDto create(CountryRegionDto dto) {
        CountryRegion entity = countryRegionMapper.toEntity(dto);
        CountryRegion saved = countryRegionRepository.save(entity);
        return countryRegionMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = COUNTRY_REGIONS_GET_BY_ID, key = "#code"),
            @CacheEvict(value = COUNTRY_REGIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_COUNTRY_REGIONS, allEntries = true)
    })
    @Timed(value = "countryRegion.update", description = "Time taken to update country region")
    public CountryRegionDto update(String code, CountryRegionDto dto) {
        CountryRegion existing = countryRegionRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("CountryRegion not found with code: " + code));

        existing.setName(dto.getName());
        // modifiedDate is auto-managed via @UpdateTimestamp

        CountryRegion updated = countryRegionRepository.save(existing);
        return countryRegionMapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = COUNTRY_REGIONS_GET_BY_ID, key = "#code"),
            @CacheEvict(value = COUNTRY_REGIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_COUNTRY_REGIONS, allEntries = true)
    })
    @Timed(value = "countryRegion.delete", description = "Time taken to delete country region")
    public void delete(String code) {
        CountryRegion entity = countryRegionRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("CountryRegion not found with code: " + code));
        countryRegionRepository.delete(entity);
    }
}
