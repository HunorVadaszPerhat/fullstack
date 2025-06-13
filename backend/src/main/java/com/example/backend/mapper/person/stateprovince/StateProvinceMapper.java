package com.example.backend.mapper.person.stateprovince;

import com.example.backend.domain.model.person.countryregion.CountryRegion;
import com.example.backend.domain.model.person.stateprovince.StateProvince;
import com.example.backend.domain.model.sales.salesterritory.SalesTerritory;
import com.example.backend.dto.person.stateprovince.StateProvinceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StateProvinceMapper {

    private final StateProvinceResolver resolver;

    public StateProvinceDto toDto(StateProvince entity) {
        if (entity == null) return null;

        return StateProvinceDto.builder()
                .stateProvinceId(entity.getStateProvinceId())
                .stateProvinceCode(entity.getStateProvinceCode())
                .countryRegionCode(entity.getCountryRegion() != null ? entity.getCountryRegion().getCountryRegionCode() : null)
                .isOnlyStateProvinceFlag(entity.getIsOnlyStateProvinceFlag())
                .name(entity.getName())
                .territoryId(entity.getSalesTerritory() != null ? entity.getSalesTerritory().getTerritoryId() : null)
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public StateProvince toEntity(StateProvinceDto dto) {
        if (dto == null) return null;

        CountryRegion countryRegion = resolver.resolveCountryRegion(dto.getCountryRegionCode());
        SalesTerritory salesTerritory = resolver.resolveSalesTerritory(dto.getTerritoryId());

        return StateProvince.builder()
                .stateProvinceId(dto.getStateProvinceId())
                .stateProvinceCode(dto.getStateProvinceCode())
                .countryRegion(countryRegion)
                .isOnlyStateProvinceFlag(dto.getIsOnlyStateProvinceFlag())
                .name(dto.getName())
                .salesTerritory(salesTerritory)
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(StateProvinceDto dto, StateProvince entity) {
        if (dto == null || entity == null) return;

        if (dto.getStateProvinceCode() != null) entity.setStateProvinceCode(dto.getStateProvinceCode());
        if (dto.getIsOnlyStateProvinceFlag() != null) entity.setIsOnlyStateProvinceFlag(dto.getIsOnlyStateProvinceFlag());
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getRowguid() != null) entity.setRowguid(dto.getRowguid());

        if (dto.getCountryRegionCode() != null) {
            CountryRegion cr = resolver.resolveCountryRegion(dto.getCountryRegionCode());
            entity.setCountryRegion(cr);
        }

        if (dto.getTerritoryId() != null) {
            SalesTerritory territory = resolver.resolveSalesTerritory(dto.getTerritoryId());
            entity.setSalesTerritory(territory);
        }
    }
}


