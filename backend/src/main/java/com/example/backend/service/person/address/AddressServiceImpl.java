package com.example.backend.service.person.address;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.stateprovince.StateProvince;
import com.example.backend.domain.repository.person.address.AddressRepository;
import com.example.backend.domain.repository.person.stateprovince.StateProvinceRepository;
import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.mapper.person.address.AddressMapper;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.annotation.Timed;
import java.util.List;

import static com.example.backend.util.person.address.AddressSpecifications.*;

@Service
@RequiredArgsConstructor // Automatically generates constructor for final fields
@Transactional           // Ensures that all DB operations in a method are atomic
public class AddressServiceImpl implements AddressService {

    // === Dependencies injected by Spring ===
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final StateProvinceRepository stateProvinceRepository;
    private final MeterRegistry meterRegistry;

    /**
     * Creates a new Address and links it to an existing StateProvince.
     * The stateProvinceId must exist in the database.
     */
    @CacheEvict(value = {"addresses", "searchAddresses"}, allEntries = true)
    @Timed(value = "address.create.time", description = "Time taken to create address")
    @Override
    public AddressDto create(AddressDto dto) {
        // Step 1: Validate and fetch the StateProvince
        StateProvince stateProvince = stateProvinceRepository.findById(dto.getStateProvinceId())
                .orElseThrow(() -> new EntityNotFoundException("StateProvince not found with ID: " + dto.getStateProvinceId()));

        // Step 2: Convert incoming DTO to Address entity
        Address address = addressMapper.toEntity(dto);

        // Step 3: Set the owning StateProvince (foreign key relationship)
        address.setStateProvince(stateProvince);

        // Step 4: Save and convert to DTO for response
        Address saved = addressRepository.save(address);
        return addressMapper.toDto(saved);
    }

    /**
     * Finds an address by its ID.
     * Throws exception if not found.
     */
    @Timed(value = "address.get_by_id.time", description = "Time taken to get address by ID")
    @Override
    public AddressDto getById(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + id));
        return addressMapper.toDto(address);
    }

    /**
     * Returns all addresses without pagination.
     * ⚠️ Not recommended for large datasets.
     */
    @Timed(value = "address.get_all_raw.time", description = "Time taken to get all addresses (non-paginated)")    @Timed(value = "address.get_all_raw.time", description = "Time taken to get all addresses (non-paginated)")
    @Override
    public List<AddressDto> getAll() {
        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toDto)
                .toList();
    }

    /**
     * Returns a paginated list of all addresses.
     * This is the preferred method for large datasets.
     *
     * Cached by page number, size, and sort to avoid redundant DB calls on repeated pagination.
     */
    @Timed(value = "address.get_all_paginated.time", description = "Time taken to get paginated addresses")    @Cacheable(
            value = "addresses",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort"
    )
    @Override
    public Page<AddressDto> getAllAddresses(Pageable pageable) {
        return addressRepository.findAll(pageable)
                .map(addressMapper::toDto);
    }

    /**
     * Updates an existing address using the provided DTO.
     * Only updates allowed fields. Throws exception if ID not found.
     */
    @Timed(value = "address.update.time", description = "Time taken to update address")
    @CacheEvict(value = {"addresses", "searchAddresses"}, allEntries = true)
    @Override
    public AddressDto update(Integer id, AddressDto dto) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + id));

        // Update mutable fields in the existing entity
        addressMapper.updateEntityFromDto(dto, existingAddress);

        // Save updated entity and return as DTO
        Address saved = addressRepository.save(existingAddress);
        return addressMapper.toDto(saved);
    }

    /**
     * Deletes an address by ID. Throws exception if not found.
     */
    @Timed(value = "address.delete.time", description = "Time taken to delete address")
    @CacheEvict(value = {"addresses", "searchAddresses"}, allEntries = true)
    @Override
    public void delete(Integer id) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + id));
        addressRepository.delete(existingAddress);
    }

    /**
     * Returns a paginated list of addresses filtered by city (partial match, case-insensitive).
     */
    @Timed(value = "address.search_by_city.time", description = "Time taken to search addresses by city")
    @Override
    public Page<AddressDto> getAddressesByCity(String city, Pageable pageable) {
        return addressRepository.findByCityContainingIgnoreCase(city, pageable)
                .map(addressMapper::toDto);
    }

    /**
     * Filters addresses using optional criteria: city, zipCode, and stateProvinceId.
     * Builds a dynamic WHERE clause using Spring Data JPA Specifications.
     *
     * Example: find addresses where city contains 'Berlin', zipCode is '10115', and stateProvinceId is 7.
     *
     * @param city             Optional city filter (case-insensitive, partial match)
     * @param postalCode          Optional postal code filter (exact match)
     * @param stateProvinceId  Optional state/province ID filter (foreign key)
     * @param pageable         Paging and sorting configuration
     * @return A page of AddressDto matching the criteria
     *
     * Caches the result based on all search parameters + pagination to avoid re-querying the same results.
     */
    @Timed(value = "address.search.time", description = "Time taken to search addresses")
    @Cacheable(
            value = "searchAddresses",
            key = "#city + '-' + #postalCode + '-' + #stateProvinceId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort"
    )
    @Override
    public Page<AddressDto> searchAddresses(String city, String postalCode, Integer stateProvinceId, Pageable pageable) {
        // Compose filters dynamically using Specification combinators
        Specification<Address> specification = Specification
                .where(hasCity(city))                  // If city is not null, adds LIKE LOWER('%city%')
                .and(hasPostalCode(postalCode))             // If postalCode is not null, adds equality match
                .and(hasStateProvinceId(stateProvinceId)); // If stateProvinceId is not null, join and match

        // Query the DB using dynamic filters and apply DTO mapping
        return addressRepository.findAll(specification, pageable)
                .map(addressMapper::toDto);
    }
}
