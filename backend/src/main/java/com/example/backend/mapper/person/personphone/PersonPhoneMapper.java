package com.example.backend.mapper.person.personphone;

import com.example.backend.domain.model.person.personphone.PersonPhone;
import com.example.backend.dto.person.personphone.PersonPhoneDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PersonPhoneMapper {

    @Mapping(source = "id.businessEntityId", target = "businessEntityId")
    @Mapping(source = "id.phoneNumber", target = "phoneNumber")
    @Mapping(source = "id.phoneNumberTypeId", target = "phoneNumberTypeId")
    PersonPhoneDto toDto(PersonPhone entity);

    @Mapping(target = "id.businessEntityId", source = "businessEntityId")
    @Mapping(target = "id.phoneNumber", source = "phoneNumber")
    @Mapping(target = "id.phoneNumberTypeId", source = "phoneNumberTypeId")
    @Mapping(target = "person", expression = "java(resolver.resolvePerson(dto.getBusinessEntityId()))")
    @Mapping(target = "phoneNumberType", expression = "java(resolver.resolvePhoneNumberType(dto.getPhoneNumberTypeId()))")
    PersonPhone toEntity(PersonPhoneDto dto, @Context PersonPhoneResolver resolver);

    @Mapping(target = "id.businessEntityId", source = "businessEntityId")
    @Mapping(target = "id.phoneNumber", source = "phoneNumber")
    @Mapping(target = "id.phoneNumberTypeId", source = "phoneNumberTypeId")
    @Mapping(target = "person", expression = "java(resolver.resolvePerson(dto.getBusinessEntityId()))")
    @Mapping(target = "phoneNumberType", expression = "java(resolver.resolvePhoneNumberType(dto.getPhoneNumberTypeId()))")
    void updateEntityFromDto(PersonPhoneDto dto, @MappingTarget PersonPhone entity, @Context PersonPhoneResolver resolver);
}

