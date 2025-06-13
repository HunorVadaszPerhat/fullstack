package com.example.backend.service.person.emailaddress;

import com.example.backend.domain.model.person.emailaddress.EmailAddress;
import com.example.backend.domain.model.person.emailaddress.EmailAddressId;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.repository.person.emailaddress.EmailAddressRepository;
import com.example.backend.domain.repository.person.person.PersonRepository;
import com.example.backend.dto.person.emailaddress.EmailAddressDto;
import com.example.backend.dto.person.emailaddress.EmailAddressIdDto;
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
    @Timed("emailAddress.get-all")
    public List<EmailAddressDto> getAll() {
        return emailAddressRepository.findAll()
                .stream()
                .map(emailAddressMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = EMAIL_ADDRESSES_GET_BY_ID, key = "#id.businessEntityId + '-' + #id.emailAddressId")
    @Timed("emailAddress.get-by-id")
    public EmailAddressDto getById(EmailAddressIdDto id) {
        EmailAddress entity = emailAddressRepository.findById(id.toEntity())
                .orElseThrow(() -> new EntityNotFoundException("EmailAddress not found with ID: " + id));
        return emailAddressMapper.toDto(entity);
    }

    @Override
    @Cacheable(value = SEARCH_EMAIL_ADDRESSES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed("emailAddress.get-paginated")
    public PagedResponse<EmailAddressDto> getPaginated(Pageable pageable) {
        Page<EmailAddress> page = emailAddressRepository.findAll(pageable);
        List<EmailAddressDto> content = page.getContent().stream().map(emailAddressMapper::toDto).toList();
        return new PagedResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMAIL_ADDRESSES_GET_BY_ID, key = "#result.id.businessEntityId + '-' + #result.id.emailAddressId", condition = "#result != null"),
            @CacheEvict(value = EMAIL_ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMAIL_ADDRESSES, allEntries = true)
    })
    @Timed("emailAddress.create")
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
            @CacheEvict(value = EMAIL_ADDRESSES_GET_BY_ID, key = "#id.businessEntityId + '-' + #id.emailAddressId"),
            @CacheEvict(value = EMAIL_ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMAIL_ADDRESSES, allEntries = true)
    })
    @Timed("emailAddress.update")
    public EmailAddressDto update(EmailAddressIdDto id, EmailAddressDto dto) {
        EmailAddress existing = emailAddressRepository.findById(id.toEntity())
                .orElseThrow(() -> new EntityNotFoundException("EmailAddress not found with ID: " + id));

        emailAddressMapper.updateEntityFromDto(dto, existing);
        return emailAddressMapper.toDto(emailAddressRepository.save(existing));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMAIL_ADDRESSES_GET_BY_ID, key = "#id.businessEntityId + '-' + #id.emailAddressId"),
            @CacheEvict(value = EMAIL_ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMAIL_ADDRESSES, allEntries = true)
    })
    @Timed("emailAddress.delete")
    public void delete(EmailAddressIdDto id) {
        EmailAddress entity = emailAddressRepository.findById(id.toEntity())
                .orElseThrow(() -> new EntityNotFoundException("EmailAddress not found with ID: " + id));
        emailAddressRepository.delete(entity);
    }
}
