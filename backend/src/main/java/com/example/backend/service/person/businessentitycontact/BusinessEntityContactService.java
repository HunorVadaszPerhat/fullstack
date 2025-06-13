package com.example.backend.service.person.businessentitycontact;

import com.example.backend.dto.person.businessentitycontact.BusinessEntityContactDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusinessEntityContactService {
    BusinessEntityContactDto create(BusinessEntityContactDto dto);
    BusinessEntityContactDto getById(Integer businessEntityId, Integer personId, Integer contactTypeId);
    List<BusinessEntityContactDto> getAll();
    PagedResponse<BusinessEntityContactDto> getPaginated(Pageable pageable);
    BusinessEntityContactDto update(BusinessEntityContactDto dto);
    void delete(Integer businessEntityId, Integer personId, Integer contactTypeId);
}

