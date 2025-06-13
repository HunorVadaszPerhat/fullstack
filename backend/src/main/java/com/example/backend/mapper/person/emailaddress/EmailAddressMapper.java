package com.example.backend.mapper.person.emailaddress;

import com.example.backend.domain.model.person.emailaddress.EmailAddress;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.dto.person.emailaddress.EmailAddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EmailAddressMapper {

    @Mapping(source = "person", target = "personId", qualifiedByName = "extractPersonId")
    EmailAddressDto toDto(EmailAddress entity);

    @Mapping(target = "person", ignore = true)
    EmailAddress toEntity(EmailAddressDto dto);

    @Mapping(target = "person", ignore = true)
    void updateEntityFromDto(EmailAddressDto dto, @MappingTarget EmailAddress entity);

    @Named("extractPersonId")
    static Integer extractPersonId(Person person) {
        return person != null ? person.getBusinessEntityId() : null;
    }
}
