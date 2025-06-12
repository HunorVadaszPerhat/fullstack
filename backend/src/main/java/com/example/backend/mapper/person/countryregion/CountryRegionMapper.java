package com.example.backend.mapper.person.countryregion;


import com.example.backend.domain.model.person.countryregion.CountryRegion;
import com.example.backend.dto.person.countryregion.CountryRegionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryRegionMapper {
    CountryRegionDto toDto(CountryRegion entity);
    CountryRegion toEntity(CountryRegionDto dto);
}
