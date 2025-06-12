package com.example.backend.service.person.emailaddress;

import com.example.backend.domain.model.person.emailaddress.EmailAddress;
import com.example.backend.domain.model.person.emailaddress.EmailAddressId;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.repository.person.emailaddress.EmailAddressRepository;
import com.example.backend.domain.repository.person.person.PersonRepository;
import com.example.backend.dto.person.emailaddress.EmailAddressDto;
import com.example.backend.mapper.person.emailaddress.EmailAddressMapper;
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
public class EmailAddressServiceImpl implements EmailAddressService {

    private final EmailAddressRepository emailAddressRepository;
    private final EmailAddressMapper emailAddressMapper;
    private final PersonRepository personRepository;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = EMAIL_ADDRESSES_GET_ALL, key = "'all'")
    @Timed(value = "emailAddress.get-all", description = "Time taken to get all email addresses (non-paginated)")
    public List<EmailAddressDto> getAll() {
        return emailAddressRepository.findAll()
                .stream()
                .map(emailAddressMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = EMAIL_ADDRESSES_GET_BY_ID, key = "#businessEntityId + '-' + #emailAddressId")
    @Timed(value = "emailAddress.get-by-id", description = "Time taken to get email address by ID")
    public EmailAddressDto getById(Integer businessEntityId, Integer emailAddressId) {
        EmailAddressId id = new EmailAddressId(businessEntityId, emailAddressId);
        EmailAddress entity = emailAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmailAddress not found with ID: " + id));
        return emailAddressMapper.toDto(entity);
    }

    @Override
    @Cacheable(value = SEARCH_EMAIL_ADDRESSES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "emailAddress.get-paginated", description = "Time taken to get paginated email addresses")
    public PagedResponse<EmailAddressDto> getAllEmailAddresses(Pageable pageable) {
        Page<EmailAddress> page = emailAddressRepository.findAll(pageable);
        List<EmailAddressDto> content = page.getContent()
                .stream()
                .map(emailAddressMapper::toDto)
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
            @CacheEvict(value = EMAIL_ADDRESSES_GET_BY_ID, key = "#result.id.businessEntityId + '-' + #result.id.emailAddressId", condition = "#result != null"),
            @CacheEvict(value = EMAIL_ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMAIL_ADDRESSES, allEntries = true)
    })
    @Timed(value = "emailAddress.create", description = "Time taken to create email address")
    public EmailAddressDto create(EmailAddressDto dto) {
        Person person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with ID: " + dto.getPersonId()));

        EmailAddress entity = emailAddressMapper.toEntity(dto);
        entity.setPerson(person);

        EmailAddress saved = emailAddressRepository.save(entity);
        return emailAddressMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMAIL_ADDRESSES_GET_BY_ID, key = "#businessEntityId + '-' + #emailAddressId"),
            @CacheEvict(value = EMAIL_ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMAIL_ADDRESSES, allEntries = true)
    })
    @Timed(value = "emailAddress.update", description = "Time taken to update email address")
    public EmailAddressDto update(Integer businessEntityId, Integer emailAddressId, EmailAddressDto dto) {
        EmailAddressId id = new EmailAddressId(businessEntityId, emailAddressId);
        EmailAddress existing = emailAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmailAddress not found with ID: " + id));

        emailAddressMapper.updateEntityFromDto(dto, existing);
        EmailAddress updated = emailAddressRepository.save(existing);
        return emailAddressMapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMAIL_ADDRESSES_GET_BY_ID, key = "#businessEntityId + '-' + #emailAddressId"),
            @CacheEvict(value = EMAIL_ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMAIL_ADDRESSES, allEntries = true)
    })
    @Timed(value = "emailAddress.delete", description = "Time taken to delete email address")
    public void delete(Integer businessEntityId, Integer emailAddressId) {
        EmailAddressId id = new EmailAddressId(businessEntityId, emailAddressId);
        EmailAddress entity = emailAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmailAddress not found with ID: " + id));
        emailAddressRepository.delete(entity);
    }
}
