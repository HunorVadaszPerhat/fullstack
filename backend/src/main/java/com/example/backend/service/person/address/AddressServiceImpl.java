package com.example.backend.service.person.address;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.model.person.stateprovince.StateProvince;
import com.example.backend.domain.repository.person.address.AddressRepository;
import com.example.backend.domain.repository.person.person.PersonRepository;
import com.example.backend.domain.repository.person.stateprovince.StateProvinceRepository;
import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.mapper.person.address.AddressMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional // @Transactional ensures data consistency
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final StateProvinceRepository stateProvinceRepository;

    /*
    * An Address must be associated with an existing StateProvinceID
    * - you must find StateProvinceID first
    * - stateProvinceId is passed into DTO provided by client/request etc.
    * */
    @Override
    public AddressDto create(AddressDto dto) {
        // Step 1: find existing StateProvinceRepository
        StateProvince stateProvince = stateProvinceRepository.findById(dto.getStateProvinceId())
                .orElseThrow(() -> new EntityNotFoundException("StateProvinceRepository not found"));
        // Step 2: map DTO to Address entity
        Address address = addressMapper.toEntity(dto);
        // Step 3: link Address to StateProvinceRepository
        address.setStateProvince(stateProvince);
        // Step 4: save and return
        Address saved = addressRepository.save(address);
        return addressMapper.toDto(saved);
    }

    @Override
    public AddressDto getById(Integer id) {
        // Step 1: Look up the Address by ID; throw an exception if not found
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        // Step 2: Convert the found Address entity to a DTO and return it
        return addressMapper.toDto(address);
    }

    @Override
    public List<AddressDto> getAll() {
        // Step 1: Retrieve all Address entities from the database
        return addressRepository.findAll()
                .stream()
                // Step 2: Convert each Address entity to a AddressDto using the mapper
                .map(addressMapper::toDto)
                // Step 3: Collect the result into a List and return
                .toList();
    }

    @Override
    public AddressDto update(Integer id, AddressDto dto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
