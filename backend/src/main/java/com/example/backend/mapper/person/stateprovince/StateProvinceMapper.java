package com.example.backend.mapper.person.stateprovince;

import com.example.backend.domain.model.person.stateprovince.StateProvince;
import com.example.backend.dto.person.stateprovince.StateProvinceDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StateProvinceMapper {

    @Mapping(source = "countryRegion.countryRegionCode", target = "countryRegionCode")
    @Mapping(source = "salesTerritory.territoryId", target = "territoryId")
    StateProvinceDto toDto(StateProvince entity);

    @Mapping(target = "countryRegion", expression = "java(resolver.resolveCountryRegion(dto.getCountryRegionCode()))")
    @Mapping(target = "salesTerritory", expression = "java(resolver.resolveSalesTerritory(dto.getTerritoryId()))")
    StateProvince toEntity(StateProvinceDto dto, @Context EntityResolver resolver);

    @Mapping(target = "countryRegion", expression = "java(resolver.resolveCountryRegion(dto.getCountryRegionCode()))")
    @Mapping(target = "salesTerritory", expression = "java(resolver.resolveSalesTerritory(dto.getTerritoryId()))")
    void updateEntityFromDto(StateProvinceDto dto, @MappingTarget StateProvince entity, @Context EntityResolver resolver);
}

