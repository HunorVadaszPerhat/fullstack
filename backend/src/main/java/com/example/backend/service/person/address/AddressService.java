package com.example.backend.service.person.address;

import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    AddressDto create(AddressDto dto);
    AddressDto getById(Integer id);
    List<AddressDto> getAll();
    PagedResponse<AddressDto> getPaginated(Pageable pageable);
    AddressDto update(Integer id, AddressDto dto);
    void delete(Integer id);
}

