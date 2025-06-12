package com.example.backend.service.person.phonenumbertype;

import com.example.backend.dto.person.phonenumbertype.PhoneNumberTypeDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PhoneNumberTypeService {
    PhoneNumberTypeDto create(PhoneNumberTypeDto dto);
    PhoneNumberTypeDto getById(Integer id);
    List<PhoneNumberTypeDto> getAll();
    PagedResponse<PhoneNumberTypeDto> getPaginated(Pageable pageable);
    PhoneNumberTypeDto update(Integer id, PhoneNumberTypeDto dto);
    void delete(Integer id);
}
