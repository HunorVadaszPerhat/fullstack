package com.example.backend.mapper.production.location;

import com.example.backend.domain.model.production.location.Location;
import com.example.backend.dto.production.location.LocationDto;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public LocationDto toDto(Location entity) {
        return LocationDto.builder()
                .locationId(entity.getLocationId())
                .name(entity.getName())
                .costRate(entity.getCostRate())
                .availability(entity.getAvailability())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public Location toEntity(LocationDto dto) {
        return Location.builder()
                .locationId(dto.getLocationId())
                .name(dto.getName())
                .costRate(dto.getCostRate())
                .availability(dto.getAvailability())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(LocationDto dto, @MappingTarget Location entity) {
        entity.setName(dto.getName());
        entity.setCostRate(dto.getCostRate());
        entity.setAvailability(dto.getAvailability());
    }
}

