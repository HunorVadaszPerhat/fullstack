package com.example.backend.service.person.emailaddress;

import com.example.backend.dto.person.emailaddress.EmailAddressDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmailAddressService {
    EmailAddressDto create(EmailAddressDto dto);
    EmailAddressDto getById(Integer businessEntityId, Integer emailAddressId);
    List<EmailAddressDto> getAll();
    PagedResponse<EmailAddressDto> getAllEmailAddresses(Pageable pageable);
    EmailAddressDto update(Integer businessEntityId, Integer emailAddressId, EmailAddressDto dto);
    void delete(Integer businessEntityId, Integer emailAddressId);
}
