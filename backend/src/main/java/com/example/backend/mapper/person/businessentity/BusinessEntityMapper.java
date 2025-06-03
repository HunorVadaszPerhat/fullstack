package com.example.backend.mapper.person.businessentity;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.dto.person.businessentity.BusinessEntityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessEntityMapper {
    BusinessEntityDto toDto(BusinessEntity entity);
    BusinessEntity toEntity(BusinessEntityDto dto);
}
