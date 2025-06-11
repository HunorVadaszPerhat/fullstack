package com.example.backend.service.person.addresstype;

import com.example.backend.dto.person.addresstype.AddressTypeDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressTypeService {
    AddressTypeDto create(AddressTypeDto dto);
    AddressTypeDto getById(Integer id);
    List<AddressTypeDto> getAll();
    PagedResponse<AddressTypeDto> getAllAddressTypes(Pageable pageable);
    AddressTypeDto update(Integer id, AddressTypeDto dto);
    void delete(Integer id);
}
