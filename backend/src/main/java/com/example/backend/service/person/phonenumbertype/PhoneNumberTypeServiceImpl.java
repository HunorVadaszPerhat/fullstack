package com.example.backend.service.person.phonenumbertype;

import com.example.backend.domain.model.person.phonenumbertype.PhoneNumberType;
import com.example.backend.domain.repository.person.phonenumbertype.PhoneNumberTypeRepository;
import com.example.backend.dto.person.phonenumbertype.PhoneNumberTypeDto;
import com.example.backend.mapper.person.phonenumbertype.PhoneNumberTypeMapper;
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
public class PhoneNumberTypeServiceImpl implements PhoneNumberTypeService {

    private final PhoneNumberTypeRepository phoneNumberTypeRepository;
    private final PhoneNumberTypeMapper phoneNumberTypeMapper;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = PHONE_NUMBER_TYPES_GET_ALL, key = "'all'")
    @Timed(value = "phoneNumberType.get-all", description = "Time taken to get all phone number types (non-paginated)")
    public List<PhoneNumberTypeDto> getAll() {
        return phoneNumberTypeRepository.findAll()
                .stream()
                .map(phoneNumberTypeMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = PHONE_NUMBER_TYPES_GET_BY_ID, key = "#id")
    @Timed(value = "phoneNumberType.get-by-id", description = "Time taken to get phone number type by ID")
    public PhoneNumberTypeDto getById(Integer id) {
        PhoneNumberType entity = phoneNumberTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PhoneNumberType not found with ID: " + id));
        return phoneNumberTypeMapper.toDto(entity);
    }

    @Override
    @Cacheable(value = SEARCH_PHONE_NUMBER_TYPES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "phoneNumberType.get-paginated", description = "Time taken to get paginated phone number types")
    public PagedResponse<PhoneNumberTypeDto> getPaginated(Pageable pageable) {
        Page<PhoneNumberType> page = phoneNumberTypeRepository.findAll(pageable);
        List<PhoneNumberTypeDto> content = page.getContent()
                .stream()
                .map(phoneNumberTypeMapper::toDto)
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
            @CacheEvict(value = PHONE_NUMBER_TYPES_GET_BY_ID, key = "#result.phoneNumberTypeId", condition = "#result != null"),
            @CacheEvict(value = PHONE_NUMBER_TYPES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PHONE_NUMBER_TYPES, allEntries = true)
    })
    @Timed(value = "phoneNumberType.create", description = "Time taken to create phone number type")
    public PhoneNumberTypeDto create(PhoneNumberTypeDto dto) {
        PhoneNumberType entity = phoneNumberTypeMapper.toEntity(dto);
        PhoneNumberType saved = phoneNumberTypeRepository.save(entity);
        return phoneNumberTypeMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PHONE_NUMBER_TYPES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = PHONE_NUMBER_TYPES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PHONE_NUMBER_TYPES, allEntries = true)
    })
    @Timed(value = "phoneNumberType.update", description = "Time taken to update phone number type")
    public PhoneNumberTypeDto update(Integer id, PhoneNumberTypeDto dto) {
        PhoneNumberType entity = phoneNumberTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PhoneNumberType not found with ID: " + id));

        phoneNumberTypeMapper.updateEntityFromDto(dto, entity);

        PhoneNumberType updated = phoneNumberTypeRepository.save(entity);
        return phoneNumberTypeMapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PHONE_NUMBER_TYPES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = PHONE_NUMBER_TYPES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PHONE_NUMBER_TYPES, allEntries = true)
    })
    @Timed(value = "phoneNumberType.delete", description = "Time taken to delete phone number type")
    public void delete(Integer id) {
        PhoneNumberType entity = phoneNumberTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PhoneNumberType not found with ID: " + id));
        phoneNumberTypeRepository.delete(entity);
    }
}
