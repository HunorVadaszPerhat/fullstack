package com.example.backend.mapper.person.password;

import com.example.backend.domain.model.person.password.Password;
import com.example.backend.dto.person.password.PasswordDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PasswordMapper {
    // Entity -> DTO
    @Mapping(source = "person.businessEntityId", target = "personId")
    PasswordDto toDto(Password entity);
    // DTO -> Entity
    @Mapping(source = "personId", target = "person.businessEntityId")
    Password toEntity(PasswordDto dto);
    // Update existing entity
    @Mapping(source = "personId", target = "person.businessEntityId")
    void updateEntityFromDto(PasswordDto dto, @MappingTarget Password entity);
}
