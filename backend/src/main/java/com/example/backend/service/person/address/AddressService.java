package com.example.backend.service.person.address;

import com.example.backend.dto.person.address.AddressDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    AddressDto create(AddressDto dto);
    AddressDto getById(Integer id);
    List<AddressDto> getAll();
    Page<AddressDto> getAllAddresses(Pageable pageable);
    AddressDto update(Integer id, AddressDto dto);
    void delete(Integer id);
    Page<AddressDto> getAddressesByCity(String city, Pageable pageable);
    Page<AddressDto> searchAddresses(String city, String zipCode, Integer stateProvinceId, Pageable pageable);
}

