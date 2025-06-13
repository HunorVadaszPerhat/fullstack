package com.example.backend.mapper.person.businessentitycontact;

import com.example.backend.domain.model.person.businessentitycontact.BusinessEntityContact;
import com.example.backend.dto.person.businessentitycontact.BusinessEntityContactDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BusinessEntityContactMapper {

    // Entity → DTO
    @Mapping(source = "id.businessEntityId", target = "businessEntityId")
    @Mapping(source = "id.personId", target = "personId")
    @Mapping(source = "id.contactTypeId", target = "contactTypeId")
    BusinessEntityContactDto toDto(BusinessEntityContact entity);

    // DTO → Entity (with injected relationships)
    @Mapping(target = "id.businessEntityId", source = "businessEntityId")
    @Mapping(target = "id.personId", source = "personId")
    @Mapping(target = "id.contactTypeId", source = "contactTypeId")
    @Mapping(target = "businessEntity", expression = "java(resolver.resolveBusinessEntity(dto.getBusinessEntityId()))")
    @Mapping(target = "person", expression = "java(resolver.resolvePerson(dto.getPersonId()))")
    @Mapping(target = "contactType", expression = "java(resolver.resolveContactType(dto.getContactTypeId()))")
    BusinessEntityContact toEntity(BusinessEntityContactDto dto, @Context BusinessEntityContactResolver resolver);

    // Update existing entity in-place
    @Mapping(target = "id.businessEntityId", source = "businessEntityId")
    @Mapping(target = "id.personId", source = "personId")
    @Mapping(target = "id.contactTypeId", source = "contactTypeId")
    @Mapping(target = "businessEntity", expression = "java(resolver.resolveBusinessEntity(dto.getBusinessEntityId()))")
    @Mapping(target = "person", expression = "java(resolver.resolvePerson(dto.getPersonId()))")
    @Mapping(target = "contactType", expression = "java(resolver.resolveContactType(dto.getContactTypeId()))")
    void updateEntityFromDto(BusinessEntityContactDto dto, @MappingTarget BusinessEntityContact entity, @Context BusinessEntityContactResolver resolver);
}
