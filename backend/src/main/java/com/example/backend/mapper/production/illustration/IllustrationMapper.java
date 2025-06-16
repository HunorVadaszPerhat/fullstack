package com.example.backend.mapper.production.illustration;

import com.example.backend.domain.model.production.illustration.Illustration;
import com.example.backend.dto.production.illustration.IllustrationDto;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
public class IllustrationMapper {

    public IllustrationDto toDto(Illustration entity) {
        if (entity == null) return null;
        return IllustrationDto.builder()
                .illustrationId(entity.getIllustrationId())
                .diagram(entity.getDiagram())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public Illustration toEntity(IllustrationDto dto) {
        if (dto == null) return null;
        return Illustration.builder()
                .illustrationId(dto.getIllustrationId())
                .diagram(dto.getDiagram())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(IllustrationDto dto, @MappingTarget Illustration entity) {
        entity.setDiagram(dto.getDiagram());
    }
}
