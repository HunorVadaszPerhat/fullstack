package com.example.backend.service.person.businessentitycontact;

import com.example.backend.domain.model.person.businessentitycontact.BusinessEntityContact;
import com.example.backend.domain.model.person.businessentitycontact.BusinessEntityContactId;
import com.example.backend.domain.repository.person.businessentitycontact.BusinessEntityContactRepository;
import com.example.backend.dto.person.businessentitycontact.BusinessEntityContactDto;
import com.example.backend.mapper.person.businessentitycontact.BusinessEntityContactMapper;
import com.example.backend.mapper.person.businessentitycontact.EntityResolver;
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
public class BusinessEntityContactServiceImpl implements BusinessEntityContactService {

    private final BusinessEntityContactRepository repository;
    private final BusinessEntityContactMapper mapper;
    private final EntityResolver entityResolver;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = BUSINESS_ENTITY_CONTACTS_GET_ALL, key = "'all'")
    @Timed(value = "businessEntityContact.get-all", description = "Time taken to get all business entity contacts (non-paginated)")
    public List<BusinessEntityContactDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = BUSINESS_ENTITY_CONTACTS_GET_BY_ID, key = "#businessEntityId + '-' + #personId + '-' + #contactTypeId")
    @Timed(value = "businessEntityContact.get-by-id", description = "Time taken to get business entity contact by ID")
    public BusinessEntityContactDto getById(Integer businessEntityId, Integer personId, Integer contactTypeId) {
        BusinessEntityContactId id = new BusinessEntityContactId(businessEntityId, personId, contactTypeId);
        BusinessEntityContact entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntityContact not found with ID: " + id));
        return mapper.toDto(entity);
    }

    @Override
    @Cacheable(value = SEARCH_BUSINESS_ENTITY_CONTACTS, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "businessEntityContact.get-paginated", description = "Time taken to get paginated business entity contacts")
    public PagedResponse<BusinessEntityContactDto> getPaginated(Pageable pageable) {
        Page<BusinessEntityContact> page = repository.findAll(pageable);
        List<BusinessEntityContactDto> content = page.getContent()
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
            @CacheEvict(value = BUSINESS_ENTITY_CONTACTS_GET_BY_ID, key = "#result.businessEntityId + '-' + #result.personId + '-' + #result.contactTypeId", condition = "#result != null"),
            @CacheEvict(value = BUSINESS_ENTITY_CONTACTS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BUSINESS_ENTITY_CONTACTS, allEntries = true)
    })
    @Timed(value = "businessEntityContact.create", description = "Time taken to create business entity contact")
    public BusinessEntityContactDto create(BusinessEntityContactDto dto) {
        BusinessEntityContact entity = mapper.toEntity(dto, entityResolver);
        entity.setId(new BusinessEntityContactId(dto.getBusinessEntityId(), dto.getPersonId(), dto.getContactTypeId()));
        entity.setRowguid(UUID.randomUUID());
        BusinessEntityContact saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = BUSINESS_ENTITY_CONTACTS_GET_BY_ID, key = "#dto.businessEntityId + '-' + #dto.personId + '-' + #dto.contactTypeId"),
            @CacheEvict(value = BUSINESS_ENTITY_CONTACTS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BUSINESS_ENTITY_CONTACTS, allEntries = true)
    })
    @Timed(value = "businessEntityContact.update", description = "Time taken to update business entity contact")
    public BusinessEntityContactDto update(BusinessEntityContactDto dto) {
        BusinessEntityContactId id = new BusinessEntityContactId(dto.getBusinessEntityId(), dto.getPersonId(), dto.getContactTypeId());
        BusinessEntityContact existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntityContact not found with ID: " + id));

        mapper.updateEntityFromDto(dto, existing, entityResolver);
        BusinessEntityContact updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = BUSINESS_ENTITY_CONTACTS_GET_BY_ID, key = "#businessEntityId + '-' + #personId + '-' + #contactTypeId"),
            @CacheEvict(value = BUSINESS_ENTITY_CONTACTS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BUSINESS_ENTITY_CONTACTS, allEntries = true)
    })
    @Timed(value = "businessEntityContact.delete", description = "Time taken to delete business entity contact")
    public void delete(Integer businessEntityId, Integer personId, Integer contactTypeId) {
        BusinessEntityContactId id = new BusinessEntityContactId(businessEntityId, personId, contactTypeId);
        BusinessEntityContact entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntityContact not found with ID: " + id));
        repository.delete(entity);
    }
}

