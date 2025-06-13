package com.example.backend.service.person.address;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.stateprovince.StateProvince;
import com.example.backend.repository.person.address.AddressRepository;
import com.example.backend.repository.person.stateprovince.StateProvinceRepository;
import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.mapper.person.address.AddressMapper;
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
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final StateProvinceRepository stateProvinceRepository;
    private final MeterRegistry meterRegistry;

    @Override
    @Cacheable(value = ADDRESSES_GET_ALL, key = "'all'")
    @Timed(value = "address.get-all", description = "Time taken to get all addresses (non-paginated)")
    public List<AddressDto> getAll() {
        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = ADDRESSES_GET_BY_ID, key = "#id")
    @Timed(value = "address.get-by-id", description = "Time taken to get address by ID")
    public AddressDto getById(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + id));
        return addressMapper.toDto(address);
    }

    @Override
    @Cacheable(value = SEARCH_ADDRESSES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "address.get-paginated", description = "Time taken to get paginated addresses")
    public PagedResponse<AddressDto> getPaginated(Pageable pageable) {
        Page<Address> addressPage = addressRepository.findAll(pageable);
        List<AddressDto> content = addressPage.getContent()
                .stream()
                .map(addressMapper::toDto)
                .toList();
        return new PagedResponse<>(
                content,
                addressPage.getNumber(),
                addressPage.getSize(),
                addressPage.getTotalElements(),
                addressPage.getTotalPages(),
                addressPage.isLast()
        );
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = ADDRESSES_GET_BY_ID, key = "#result.addressId", condition = "#result != null"),
            @CacheEvict(value = ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_ADDRESSES, allEntries = true)
    })
    @Timed(value = "address.create", description = "Time taken to create address")
    public AddressDto create(AddressDto dto) {
        StateProvince stateProvince = stateProvinceRepository.findById(dto.getStateProvinceId())
                .orElseThrow(() -> new EntityNotFoundException("StateProvince not found with ID: " + dto.getStateProvinceId()));
        Address address = addressMapper.toEntity(dto);
        address.setStateProvince(stateProvince);
        Address saved = addressRepository.save(address);
        return addressMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = ADDRESSES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_ADDRESSES, allEntries = true)
    })
    @Timed(value = "address.update", description = "Time taken to update address")
    public AddressDto update(Integer id, AddressDto dto) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + id));
        addressMapper.updateEntityFromDto(dto, existingAddress);
        Address saved = addressRepository.save(existingAddress);
        return addressMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = ADDRESSES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = ADDRESSES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_ADDRESSES, allEntries = true)
    })
    @Timed(value = "address.delete", description = "Time taken to delete address")
    public void delete(Integer id) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + id));
        addressRepository.delete(existingAddress);
    }
}
