package com.example.backend.mapper.person.countryregion;


import com.example.backend.domain.model.person.countryregion.CountryRegion;
import com.example.backend.dto.person.countryregion.CountryRegionDto;
import org.springframework.stereotype.Component;


@Component
public class CountryRegionMapper {

    public CountryRegionDto toDto(CountryRegion entity) {
        if (entity == null) return null;

        return CountryRegionDto.builder()
                .countryRegionCode(entity.getCountryRegionCode())
                .name(entity.getName())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public CountryRegion toEntity(CountryRegionDto dto) {
        if (dto == null) return null;

        return CountryRegion.builder()
                .countryRegionCode(dto.getCountryRegionCode())
                .name(dto.getName())
                .modifiedDate(dto.getModifiedDate()) // @UpdateTimestamp normally manages this
                .build();
    }

    public void updateEntityFromDto(CountryRegionDto dto, CountryRegion entity) {
        if (dto == null || entity == null) return;

        if (dto.getName() != null) entity.setName(dto.getName());
        // Typically skip modifiedDate — it's handled automatically
    }
}
