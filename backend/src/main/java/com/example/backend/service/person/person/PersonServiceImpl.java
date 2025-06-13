package com.example.backend.service.person.person;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.repository.person.businessentity.BusinessEntityRepository;
import com.example.backend.domain.repository.person.person.PersonRepository;
import com.example.backend.dto.person.person.PersonDto;
import com.example.backend.mapper.person.person.PersonMapper;
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
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final BusinessEntityRepository businessEntityRepository;
    private final PersonMapper personMapper;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = PERSONS_GET_ALL, key = "'all'")
    @Timed(value = "person.get-all", description = "Time taken to get all persons (non-paginated)")
    public List<PersonDto> getAll() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = PERSONS_GET_BY_ID, key = "#id")
    @Timed(value = "person.get-by-id", description = "Time taken to get person by ID")
    public PersonDto getById(Integer id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with ID: " + id));
        return personMapper.toDto(person);
    }

    @Override
    @Cacheable(value = SEARCH_PERSONS, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "person.get-paginated", description = "Time taken to get paginated persons")
    public PagedResponse<PersonDto> getPaginated(Pageable pageable) {
        Page<Person> page = personRepository.findAll(pageable);
        List<PersonDto> content = page.getContent()
                .stream()
                .map(personMapper::toDto)
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
            @CacheEvict(value = PERSONS_GET_BY_ID, key = "#result.businessEntityId", condition = "#result != null"),
            @CacheEvict(value = PERSONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PERSONS, allEntries = true)
    })
    @Timed(value = "person.create", description = "Time taken to create person")
    public PersonDto create(PersonDto dto) {
        BusinessEntity businessEntity = businessEntityRepository.findById(dto.getBusinessEntityId())
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found with ID: " + dto.getBusinessEntityId()));

        Person person = personMapper.toEntity(dto);
        person.setBusinessEntity(businessEntity);

        Person saved = personRepository.save(person);
        return personMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PERSONS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = PERSONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PERSONS, allEntries = true)
    })
    @Timed(value = "person.update", description = "Time taken to update person")
    public PersonDto update(Integer id, PersonDto dto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with ID: " + id));

        personMapper.updateEntityFromDto(dto, person);

        if (dto.getBusinessEntityId() != null) {
            BusinessEntity be = businessEntityRepository.findById(dto.getBusinessEntityId())
                    .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found with ID: " + dto.getBusinessEntityId()));
            person.setBusinessEntity(be);
        }

        Person saved = personRepository.save(person);
        return personMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PERSONS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = PERSONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PERSONS, allEntries = true)
    })
    @Timed(value = "person.delete", description = "Time taken to delete person")
    public void delete(Integer id) {
        if (!personRepository.existsById(id)) {
            throw new EntityNotFoundException("Person not found with ID: " + id);
        }
        personRepository.deleteById(id);
    }
}

