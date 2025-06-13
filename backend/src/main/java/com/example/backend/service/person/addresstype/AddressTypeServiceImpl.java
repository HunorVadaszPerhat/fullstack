package com.example.backend.service.person.addresstype;

import com.example.backend.domain.model.person.addresstype.AddressType;
import com.example.backend.repository.person.addresstype.AddressTypeRepository;
import com.example.backend.dto.person.addresstype.AddressTypeDto;
import com.example.backend.mapper.person.addresstype.AddressTypeMapper;
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
public class AddressTypeServiceImpl implements AddressTypeService {
    private final AddressTypeRepository addressTypeRepository;
    private final AddressTypeMapper addressTypeMapper;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = ADDRESS_TYPES_GET_ALL, key = "'all'")
    @Timed(value = "address-type.get-all", description = "Time taken to get all address types (non-paginated)")
    public List<AddressTypeDto> getAll() {
        return addressTypeRepository.findAll()
                .stream()
                .map(addressTypeMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = ADDRESS_TYPES_GET_BY_ID, key = "#id")
    @Timed(value = "address-type.get-by-id", description = "Time taken to get address type by ID")
    public AddressTypeDto getById(Integer id) {
        AddressType addressType = addressTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address type not found with ID: " + id));
        return addressTypeMapper.toDto(addressType);
    }

    @Override
    @Cacheable(value = SEARCH_ADDRESS_TYPES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "address-type.get-paginated", description = "Time taken to get paginated address types")
    public PagedResponse<AddressTypeDto> getAllAddressTypes(Pageable pageable) {
        Page<AddressType> addressTypePage = addressTypeRepository.findAll(pageable);
        List<AddressTypeDto> content = addressTypePage.getContent()
                .stream()
                .map(addressTypeMapper::toDto)
                .toList();
        return new PagedResponse<>(
                content,
                addressTypePage.getNumber(),
                addressTypePage.getSize(),
                addressTypePage.getTotalElements(),
                addressTypePage.getTotalPages(),
                addressTypePage.isLast()
        );
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = ADDRESS_TYPES_GET_BY_ID, key = "#result.addressTypeId", condition = "#result != null"),
            @CacheEvict(value = ADDRESS_TYPES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_ADDRESS_TYPES, allEntries = true)
    })
    @Timed(value = "address-type.create", description = "Time taken to create address type")
    public AddressTypeDto create(AddressTypeDto dto) {
        AddressType addressType = addressTypeMapper.toEntity(dto);
        addressTypeRepository.save(addressType);
        return addressTypeMapper.toDto(addressType);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = ADDRESS_TYPES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = ADDRESS_TYPES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_ADDRESS_TYPES, allEntries = true)
    })
    @Timed(value = "address-type.update", description = "Time taken to update address type")
    public AddressTypeDto update(Integer id, AddressTypeDto dto) {
        AddressType existingAddressType = addressTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address type not found with ID: " + id));
        addressTypeMapper.updateEntityFromDto(dto, existingAddressType);
        AddressType saved = addressTypeRepository.save(existingAddressType);
        return addressTypeMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = ADDRESS_TYPES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = ADDRESS_TYPES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_ADDRESS_TYPES, allEntries = true)
    })
    @Timed(value = "address-type.delete", description = "Time taken to delete address type")
    public void delete(Integer id) {
        AddressType existingAddressType = addressTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address type not found with ID: " + id));
        addressTypeRepository.delete(existingAddressType);
    }
}
