package com.example.backend.service.person.personphone;

import com.example.backend.dto.person.personphone.PersonPhoneDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonPhoneService {
    PersonPhoneDto create(PersonPhoneDto dto);
    PersonPhoneDto getById(Integer businessEntityId, String phoneNumber, Integer phoneNumberTypeId);
    List<PersonPhoneDto> getAll();
    PagedResponse<PersonPhoneDto> getPaginated(Pageable pageable);
    PersonPhoneDto update(PersonPhoneDto dto);
    void delete(Integer businessEntityId, String phoneNumber, Integer phoneNumberTypeId);
}
