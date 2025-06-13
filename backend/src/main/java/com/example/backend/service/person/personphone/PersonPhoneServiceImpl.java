package com.example.backend.service.person.personphone;

import com.example.backend.domain.model.person.personphone.PersonPhone;
import com.example.backend.domain.model.person.personphone.PersonPhoneId;
import com.example.backend.domain.repository.person.personphone.PersonPhoneRepository;
import com.example.backend.dto.person.personphone.PersonPhoneDto;
import com.example.backend.mapper.person.personphone.EntityResolver;
import com.example.backend.mapper.person.personphone.PersonPhoneMapper;
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
public class PersonPhoneServiceImpl implements PersonPhoneService {

    private final PersonPhoneRepository repository;
    private final PersonPhoneMapper mapper;
    private final EntityResolver entityResolver;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = PERSON_PHONES_GET_ALL, key = "'all'")
    @Timed(value = "personPhone.get-all", description = "Time taken to get all person phones (non-paginated)")
    public List<PersonPhoneDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = PERSON_PHONES_GET_BY_ID, key = "#businessEntityId + '-' + #phoneNumber + '-' + #phoneNumberTypeId")
    @Timed(value = "personPhone.get-by-id", description = "Time taken to get person phone by ID")
    public PersonPhoneDto getById(Integer businessEntityId, String phoneNumber, Integer phoneNumberTypeId) {
        PersonPhoneId id = new PersonPhoneId(businessEntityId, phoneNumber, phoneNumberTypeId);
        PersonPhone entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PersonPhone not found with ID: " + id));
        return mapper.toDto(entity);
    }

    @Override
    @Cacheable(value = SEARCH_PERSON_PHONES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "personPhone.get-paginated", description = "Time taken to get paginated person phones")
    public PagedResponse<PersonPhoneDto> getPaginated(Pageable pageable) {
        Page<PersonPhone> page = repository.findAll(pageable);
        List<PersonPhoneDto> content = page.getContent()
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
            @CacheEvict(value = PERSON_PHONES_GET_BY_ID, key = "#result.businessEntityId + '-' + #result.phoneNumber + '-' + #result.phoneNumberTypeId", condition = "#result != null"),
            @CacheEvict(value = PERSON_PHONES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PERSON_PHONES, allEntries = true)
    })
    @Timed(value = "personPhone.create", description = "Time taken to create person phone")
    public PersonPhoneDto create(PersonPhoneDto dto) {
        PersonPhone entity = mapper.toEntity(dto, entityResolver);
        entity.setId(new PersonPhoneId(dto.getBusinessEntityId(), dto.getPhoneNumber(), dto.getPhoneNumberTypeId()));
        PersonPhone saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PERSON_PHONES_GET_BY_ID, key = "#dto.businessEntityId + '-' + #dto.phoneNumber + '-' + #dto.phoneNumberTypeId"),
            @CacheEvict(value = PERSON_PHONES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PERSON_PHONES, allEntries = true)
    })
    @Timed(value = "personPhone.update", description = "Time taken to update person phone")
    public PersonPhoneDto update(PersonPhoneDto dto) {
        PersonPhoneId id = new PersonPhoneId(dto.getBusinessEntityId(), dto.getPhoneNumber(), dto.getPhoneNumberTypeId());
        PersonPhone existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PersonPhone not found with ID: " + id));

        mapper.updateEntityFromDto(dto, existing, entityResolver);
        PersonPhone updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PERSON_PHONES_GET_BY_ID, key = "#businessEntityId + '-' + #phoneNumber + '-' + #phoneNumberTypeId"),
            @CacheEvict(value = PERSON_PHONES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PERSON_PHONES, allEntries = true)
    })
    @Timed(value = "personPhone.delete", description = "Time taken to delete person phone")
    public void delete(Integer businessEntityId, String phoneNumber, Integer phoneNumberTypeId) {
        PersonPhoneId id = new PersonPhoneId(businessEntityId, phoneNumber, phoneNumberTypeId);
        PersonPhone entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PersonPhone not found with ID: " + id));
        repository.delete(entity);
    }
}

