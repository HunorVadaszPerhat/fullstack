package com.example.backend.service.person.emailaddress;

import com.example.backend.dto.person.emailaddress.EmailAddressDto;
import com.example.backend.dto.person.emailaddress.EmailAddressIdDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmailAddressService {
    EmailAddressDto create(EmailAddressDto dto);
    EmailAddressDto getById(EmailAddressIdDto id);
    List<EmailAddressDto> getAll();
    PagedResponse<EmailAddressDto> getPaginated(Pageable pageable);
    EmailAddressDto update(EmailAddressIdDto id, EmailAddressDto dto);
    void delete(EmailAddressIdDto id);
}
