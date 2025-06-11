package com.example.backend.service.person.addresstype;

import com.example.backend.dto.person.addresstype.AddressTypeDto;
import com.example.backend.util.response.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressTypeServiceImpl implements AddressTypeService {
    @Override
    public AddressTypeDto create(AddressTypeDto dto) {
        return null;
    }

    @Override
    public AddressTypeDto getById(Integer id) {
        return null;
    }

    @Override
    public List<AddressTypeDto> getAll() {
        return List.of();
    }

    @Override
    public PagedResponse<AddressTypeDto> getAllAddressTypes(Pageable pageable) {
        return null;
    }

    @Override
    public AddressTypeDto update(Integer id, AddressTypeDto dto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
