package com.example.backend.service.person.businessentity;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.repository.person.businessentity.BusinessEntityRepository;
import com.example.backend.dto.person.businessentity.BusinessEntityDto;
import com.example.backend.mapper.person.businessentity.BusinessEntityMapper;
import com.example.backend.util.response.PagedResponse;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

import static com.example.backend.constants.CacheNames.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BusinessEntityServiceImpl implements BusinessEntityService {

    private final BusinessEntityRepository businessEntityRepository;
    private final BusinessEntityMapper businessEntityMapper;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = BUSINESS_ENTITIES_GET_ALL, key = "'all'")
    @Timed(value = "businessEntity.get-all", description = "Time taken to get all business entities (non-paginated)")
    public List<BusinessEntityDto> getAll() {
        return businessEntityRepository.findAll()
                .stream()
                .map(businessEntityMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = BUSINESS_ENTITIES_GET_BY_ID, key = "#id")
    @Timed(value = "businessEntity.get-by-id", description = "Time taken to get business entity by ID")
    public BusinessEntityDto getById(Integer id) {
        BusinessEntity entity = businessEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found with ID: " + id));
        return businessEntityMapper.toDto(entity);
    }

    @Override
    @Cacheable(value = SEARCH_BUSINESS_ENTITIES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "businessEntity.get-paginated", description = "Time taken to get paginated business entities")
    public PagedResponse<BusinessEntityDto> getPaginated(Pageable pageable) {
        Page<BusinessEntity> page = businessEntityRepository.findAll(pageable);
        List<BusinessEntityDto> content = page.getContent()
                .stream()
                .map(businessEntityMapper::toDto)
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
            @CacheEvict(value = BUSINESS_ENTITIES_GET_BY_ID, key = "#result.businessEntityId", condition = "#result != null"),
            @CacheEvict(value = BUSINESS_ENTITIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BUSINESS_ENTITIES, allEntries = true)
    })
    @Timed(value = "businessEntity.create", description = "Time taken to create business entity")
    public BusinessEntityDto create(BusinessEntityDto dto) {
        BusinessEntity entity = businessEntityMapper.toEntity(dto);
        BusinessEntity saved = businessEntityRepository.save(entity);
        return businessEntityMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = BUSINESS_ENTITIES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = BUSINESS_ENTITIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BUSINESS_ENTITIES, allEntries = true)
    })
    @Timed(value = "businessEntity.update", description = "Time taken to update business entity")
    public BusinessEntityDto update(Integer id, BusinessEntityDto dto) {
        BusinessEntity entity = businessEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found with ID: " + id));

        entity.setRowguid(dto.getRowguid());
        // entity.setModifiedDate(dto.getModifiedDate()); // Uncomment if editable

        BusinessEntity updated = businessEntityRepository.save(entity);
        return businessEntityMapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = BUSINESS_ENTITIES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = BUSINESS_ENTITIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BUSINESS_ENTITIES, allEntries = true)
    })
    @Timed(value = "businessEntity.delete", description = "Time taken to delete business entity")
    public void delete(Integer id) {
        BusinessEntity entity = businessEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found with ID: " + id));
        businessEntityRepository.delete(entity);
    }
}
