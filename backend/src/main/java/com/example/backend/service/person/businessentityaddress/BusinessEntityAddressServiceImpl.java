package com.example.backend.service.person.businessentityaddress;

import com.example.backend.domain.model.person.businessentityaddress.BusinessEntityAddress;
import com.example.backend.domain.model.person.businessentityaddress.BusinessEntityAddressId;
import com.example.backend.domain.repository.person.businessentityaddress.BusinessEntityAddressRepository;
import com.example.backend.dto.person.businessentityaddress.BusinessEntityAddressDto;
import com.example.backend.mapper.person.businessentityaddress.BusinessEntityAddressMapper;
import com.example.backend.mapper.person.businessentityaddress.BusinessEntityAddressResolver;
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
public class BusinessEntityAddressServiceImpl implements BusinessEntityAddressService {

    private final BusinessEntityAddressRepository repository;
    private final BusinessEntityAddressMapper mapper;
    private final BusinessEntityAddressResolver resolver;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = BUSINESS_ENTITY_ADDRESSES_GET_ALL, key = "'all'")
    @Timed(value = "businessEntityAddress.get-all", description = "Time taken to get all business entity addresses (non-paginated)")
    public List<BusinessEntityAddressDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = BUSINESS_ENTITY_ADDRESSES_GET_BY_ID, key = "#businessEntityID + '-' + #addressID + '-' + #addressTypeID")
    @Timed(value = "businessEntityAddress.get-by-id", description = "Time taken to get business entity address by ID")
    public BusinessEntityAddressDto getById(Integer businessEntityID, Integer addressID, Integer addressTypeID) {
        BusinessEntityAddressId id = new BusinessEntityAddressId(businessEntityID, addressID, addressTypeID);
        BusinessEntityAddress entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntityAddress not found with ID: " + id));
        return mapper.toDto(entity);
    }

    @Override
    @Cacheable(value = SEARCH_BUSINESS_ENTITY_ADDRESSES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "businessEntityAddress.get-paginated", description = "Time taken to get paginated business entity addresses")
    public PagedResponse<BusinessEntityAddressDto> getPaginated(Pageable pageable) {
        Page<BusinessEntityAddress> page = repository.findAll(pageable);
        List<BusinessEntityAddressDto> content = page.getContent()
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
            @CacheEvict(value = BUSINESS_ENTITY_ADDRESSES_GET_BY_ID, key = "#result.businessEntityID + '-' + #result.addressID + '-' + #result.addressTypeID", condition = "#result != null"),
            @CacheEvict(value = BUSINESS_ENTITY_ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BUSINESS_ENTITY_ADDRESSES, allEntries = true)
    })
    @Timed(value = "businessEntityAddress.create", description = "Time taken to create business entity address")
    public BusinessEntityAddressDto create(BusinessEntityAddressDto dto) {
        BusinessEntityAddress entity = mapper.toEntity(dto, resolver);
        entity.setRowguid(UUID.randomUUID());

        BusinessEntityAddress saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = BUSINESS_ENTITY_ADDRESSES_GET_BY_ID, key = "#dto.businessEntityID + '-' + #dto.addressID + '-' + #dto.addressTypeID"),
            @CacheEvict(value = BUSINESS_ENTITY_ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BUSINESS_ENTITY_ADDRESSES, allEntries = true)
    })
    @Timed(value = "businessEntityAddress.update", description = "Time taken to update business entity address")
    public BusinessEntityAddressDto update(BusinessEntityAddressDto dto) {
        BusinessEntityAddressId id = new BusinessEntityAddressId(dto.getBusinessEntityID(), dto.getAddressID(), dto.getAddressTypeID());
        BusinessEntityAddress entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntityAddress not found with ID: " + id));

        mapper.updateEntityFromDto(dto, entity, resolver);
        entity.setRowguid(dto.getRowguid()); // optional override

        BusinessEntityAddress updated = repository.save(entity);
        return mapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = BUSINESS_ENTITY_ADDRESSES_GET_BY_ID, key = "#businessEntityID + '-' + #addressID + '-' + #addressTypeID"),
            @CacheEvict(value = BUSINESS_ENTITY_ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BUSINESS_ENTITY_ADDRESSES, allEntries = true)
    })
    @Timed(value = "businessEntityAddress.delete", description = "Time taken to delete business entity address")
    public void delete(Integer businessEntityID, Integer addressID, Integer addressTypeID) {
        BusinessEntityAddressId id = new BusinessEntityAddressId(businessEntityID, addressID, addressTypeID);
        BusinessEntityAddress entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntityAddress not found with ID: " + id));
        repository.delete(entity);
    }
}

