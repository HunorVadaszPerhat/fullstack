package com.example.backend.service.person.businessentityaddress;

import com.example.backend.dto.person.businessentityaddress.BusinessEntityAddressDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusinessEntityAddressService {
    BusinessEntityAddressDto create(BusinessEntityAddressDto dto);
    BusinessEntityAddressDto getById(Integer businessEntityID, Integer addressID, Integer addressTypeID);
    List<BusinessEntityAddressDto> getAll();
    PagedResponse<BusinessEntityAddressDto> getPaginated(Pageable pageable);
    BusinessEntityAddressDto update(BusinessEntityAddressDto dto);
    void delete(Integer businessEntityID, Integer addressID, Integer addressTypeID);
}


