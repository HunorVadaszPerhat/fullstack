package com.example.backend.service.person.businessentity;

import com.example.backend.dto.person.businessentity.BusinessEntityDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusinessEntityService {
    BusinessEntityDto create(BusinessEntityDto dto);
    BusinessEntityDto getById(Integer id);
    List<BusinessEntityDto> getAll();
    PagedResponse<BusinessEntityDto> getPaginated(Pageable pageable);
    BusinessEntityDto update(Integer id, BusinessEntityDto dto);
    void delete(Integer id);
}
