package com.example.backend.service.person.businessentity;

import com.example.backend.dto.person.businessentity.BusinessEntityDto;

import java.util.List;

public interface BusinessEntityService {
    BusinessEntityDto create(BusinessEntityDto dto);
    BusinessEntityDto getById(Integer id);
    List<BusinessEntityDto> getAll();
    BusinessEntityDto update(Integer id, BusinessEntityDto dto);
    void delete(Integer id);
}
