package com.example.backend.service.person.address;

import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.dto.person.person.PersonDto;

import java.util.List;

public interface AddressService {
    AddressDto create(AddressDto dto);
    AddressDto getById(Integer id);
    List<AddressDto> getAll();
    AddressDto update(Integer id, AddressDto dto);
    void delete(Integer id);
}
