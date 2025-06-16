package com.example.backend.mapper.production.culture;

import com.example.backend.domain.model.production.culture.Culture;
import com.example.backend.dto.production.culture.CultureDto;
import org.springframework.stereotype.Component;

@Component
public class CultureMapper {

    public CultureDto toDto(Culture entity) {
        if (entity == null) return null;

        return CultureDto.builder()
                .cultureId(entity.getCultureId())
                .name(entity.getName())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public Culture toEntity(CultureDto dto) {
        if (dto == null) return null;

        return Culture.builder()
                .cultureId(dto.getCultureId())
                .name(dto.getName())
                .build();
    }

    public void updateEntityFromDto(CultureDto dto, Culture entity) {
        if (dto == null || entity == null) return;

        entity.setName(dto.getName());
    }
}
