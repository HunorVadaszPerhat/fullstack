package com.example.backend.service.person.contacttype;

import com.example.backend.domain.model.person.contacttype.ContactType;
import com.example.backend.repository.person.contacttype.ContactTypeRepository;
import com.example.backend.dto.person.contacttype.ContactTypeDto;
import com.example.backend.mapper.person.contacttype.ContactTypeMapper;
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
public class ContactTypeServiceImpl implements ContactTypeService {

    private final ContactTypeRepository contactTypeRepository;
    private final ContactTypeMapper contactTypeMapper;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = CONTACT_TYPES_GET_ALL, key = "'all'")
    @Timed(value = "contactType.get-all", description = "Time taken to get all contact types (non-paginated)")
    public List<ContactTypeDto> getAll() {
        return contactTypeRepository.findAll()
                .stream()
                .map(contactTypeMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = CONTACT_TYPES_GET_BY_ID, key = "#id")
    @Timed(value = "contactType.get-by-id", description = "Time taken to get contact type by ID")
    public ContactTypeDto getById(Integer id) {
        ContactType contactType = contactTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ContactType not found with ID: " + id));
        return contactTypeMapper.toDto(contactType);
    }

    @Override
    @Cacheable(value = SEARCH_CONTACT_TYPES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "contactType.get-paginated", description = "Time taken to get paginated contact types")
    public PagedResponse<ContactTypeDto> getPaginated(Pageable pageable) {
        Page<ContactType> contactTypePage = contactTypeRepository.findAll(pageable);
        List<ContactTypeDto> content = contactTypePage.getContent()
                .stream()
                .map(contactTypeMapper::toDto)
                .toList();
        return new PagedResponse<>(
                content,
                contactTypePage.getNumber(),
                contactTypePage.getSize(),
                contactTypePage.getTotalElements(),
                contactTypePage.getTotalPages(),
                contactTypePage.isLast()
        );
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CONTACT_TYPES_GET_BY_ID, key = "#result.contactTypeId", condition = "#result != null"),
            @CacheEvict(value = CONTACT_TYPES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_CONTACT_TYPES, allEntries = true)
    })
    @Timed(value = "contactType.create", description = "Time taken to create contact type")
    public ContactTypeDto create(ContactTypeDto dto) {
        ContactType contactType = contactTypeMapper.toEntity(dto);
        ContactType saved = contactTypeRepository.save(contactType);
        return contactTypeMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CONTACT_TYPES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = CONTACT_TYPES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_CONTACT_TYPES, allEntries = true)
    })
    @Timed(value = "contactType.update", description = "Time taken to update contact type")
    public ContactTypeDto update(Integer id, ContactTypeDto dto) {
        ContactType contactType = contactTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ContactType not found with ID: " + id));

        contactType.setName(dto.getName());
        // Normally, modifiedDate is auto-handled by @UpdateTimestamp

        ContactType updated = contactTypeRepository.save(contactType);
        return contactTypeMapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CONTACT_TYPES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = CONTACT_TYPES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_CONTACT_TYPES, allEntries = true)
    })
    @Timed(value = "contactType.delete", description = "Time taken to delete contact type")
    public void delete(Integer id) {
        ContactType contactType = contactTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ContactType not found with ID: " + id));
        contactTypeRepository.delete(contactType);
    }
}

