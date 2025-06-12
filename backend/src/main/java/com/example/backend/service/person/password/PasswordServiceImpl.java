package com.example.backend.service.person.password;

import com.example.backend.domain.model.person.password.Password;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.repository.person.password.PasswordRepository;
import com.example.backend.domain.repository.person.person.PersonRepository;
import com.example.backend.dto.person.password.PasswordDto;
import com.example.backend.mapper.person.password.PasswordMapper;
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
public class PasswordServiceImpl implements PasswordService {

    private final PasswordRepository passwordRepository;
    private final PasswordMapper passwordMapper;
    private final PersonRepository personRepository;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = PASSWORDS_GET_ALL, key = "'all'")
    @Timed(value = "password.get-all", description = "Time taken to get all passwords (non-paginated)")
    public List<PasswordDto> getAll() {
        return passwordRepository.findAll()
                .stream()
                .map(passwordMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = PASSWORDS_GET_BY_ID, key = "#personId")
    @Timed(value = "password.get-by-id", description = "Time taken to get password by person ID")
    public PasswordDto getById(Integer personId) {
        Password entity = passwordRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Password not found for person ID: " + personId));
        return passwordMapper.toDto(entity);
    }

    @Override
    @Cacheable(value = SEARCH_PASSWORDS, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "password.get-paginated", description = "Time taken to get paginated passwords")
    public PagedResponse<PasswordDto> getPaginated(Pageable pageable) {
        Page<Password> page = passwordRepository.findAll(pageable);
        List<PasswordDto> content = page.getContent()
                .stream()
                .map(passwordMapper::toDto)
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
            @CacheEvict(value = PASSWORDS_GET_BY_ID, key = "#result.personId", condition = "#result != null"),
            @CacheEvict(value = PASSWORDS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PASSWORDS, allEntries = true)
    })
    @Timed(value = "password.create", description = "Time taken to create password")
    public PasswordDto create(PasswordDto dto) {
        Person person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with ID: " + dto.getPersonId()));

        Password entity = passwordMapper.toEntity(dto);
        entity.setPerson(person);

        Password saved = passwordRepository.save(entity);
        return passwordMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PASSWORDS_GET_BY_ID, key = "#personId"),
            @CacheEvict(value = PASSWORDS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PASSWORDS, allEntries = true)
    })
    @Timed(value = "password.update", description = "Time taken to update password")
    public PasswordDto update(Integer personId, PasswordDto dto) {
        Password existing = passwordRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Password not found for person ID: " + personId));

        passwordMapper.updateEntityFromDto(dto, existing);
        Password updated = passwordRepository.save(existing);
        return passwordMapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PASSWORDS_GET_BY_ID, key = "#personId"),
            @CacheEvict(value = PASSWORDS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PASSWORDS, allEntries = true)
    })
    @Timed(value = "password.delete", description = "Time taken to delete password")
    public void delete(Integer personId) {
        Password entity = passwordRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Password not found for person ID: " + personId));
        passwordRepository.delete(entity);
    }
}
