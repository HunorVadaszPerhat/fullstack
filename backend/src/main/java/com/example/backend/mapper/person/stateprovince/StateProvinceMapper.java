package com.example.backend.mapper.person.stateprovince;

import com.example.backend.domain.model.person.countryregion.CountryRegion;
import com.example.backend.domain.model.person.stateprovince.StateProvince;
import com.example.backend.domain.model.sales.salesterritory.SalesTerritory;
import com.example.backend.dto.person.stateprovince.StateProvinceDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StateProvinceMapper {

    @Mapping(source = "countryRegion", target = "countryRegionCode", qualifiedByName = "extractCountryRegionCode")
    @Mapping(source = "salesTerritory", target = "territoryId", qualifiedByName = "extractTerritoryId")
    StateProvinceDto toDto(StateProvince entity);

    @Mapping(target = "countryRegion", expression = "java(resolver.resolveCountryRegion(dto.getCountryRegionCode()))")
    @Mapping(target = "salesTerritory", expression = "java(resolver.resolveSalesTerritory(dto.getTerritoryId()))")
    StateProvince toEntity(StateProvinceDto dto, @Context StateProvinceResolver resolver);

    @Mapping(target = "countryRegion", expression = "java(resolver.resolveCountryRegion(dto.getCountryRegionCode()))")
    @Mapping(target = "salesTerritory", expression = "java(resolver.resolveSalesTerritory(dto.getTerritoryId()))")
    void updateEntityFromDto(StateProvinceDto dto, @MappingTarget StateProvince entity, @Context StateProvinceResolver resolver);

    @Named("extractCountryRegionCode")
    static String extractCountryRegionCode(CountryRegion countryRegion) {
        return countryRegion != null ? countryRegion.getCountryRegionCode() : null;
    }

    @Named("extractTerritoryId")
    static Integer extractTerritoryId(SalesTerritory salesTerritory) {
        return salesTerritory != null ? salesTerritory.getTerritoryId() : null;
    }
}


