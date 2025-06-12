package com.example.backend.mapper.person.emailaddress;

import com.example.backend.domain.model.person.emailaddress.EmailAddress;
import com.example.backend.dto.person.emailaddress.EmailAddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmailAddressMapper {
    @Mapping(source = "person.businessEntityId", target = "personId")
    EmailAddressDto toDto(EmailAddress entity);

    @Mapping(source = "personId", target = "person.businessEntityId")
    EmailAddress toEntity(EmailAddressDto dto);

    @Mapping(source = "personId", target = "person.businessEntityId")
    void updateEntityFromDto(EmailAddressDto dto, @MappingTarget EmailAddress entity);
}
