package com.example.backend.mapper.person.businessentityaddress;

import com.example.backend.domain.model.person.businessentityaddress.BusinessEntityAddress;
import com.example.backend.dto.person.businessentityaddress.BusinessEntityAddressDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { EntityResolver.class })
public interface BusinessEntityAddressMapper {

    @Mapping(source = "id.businessEntityId", target = "businessEntityID")
    @Mapping(source = "id.addressId", target = "addressID")
    @Mapping(source = "id.addressTypeId", target = "addressTypeID")
    BusinessEntityAddressDto toDto(BusinessEntityAddress entity);

    @Mapping(target = "id.businessEntityId", source = "businessEntityID")
    @Mapping(target = "id.addressId", source = "addressID")
    @Mapping(target = "id.addressTypeId", source = "addressTypeID")
    @Mapping(target = "businessEntity", expression = "java(resolver.resolveBusinessEntity(dto.getBusinessEntityID()))")
    @Mapping(target = "address", expression = "java(resolver.resolveAddress(dto.getAddressID()))")
    @Mapping(target = "addressType", expression = "java(resolver.resolveAddressType(dto.getAddressTypeID()))")
    BusinessEntityAddress toEntity(BusinessEntityAddressDto dto, @Context EntityResolver resolver);

    @Mapping(target = "id.businessEntityId", source = "businessEntityID")
    @Mapping(target = "id.addressId", source = "addressID")
    @Mapping(target = "id.addressTypeId", source = "addressTypeID")
    @Mapping(target = "businessEntity", expression = "java(resolver.resolveBusinessEntity(dto.getBusinessEntityID()))")
    @Mapping(target = "address", expression = "java(resolver.resolveAddress(dto.getAddressID()))")
    @Mapping(target = "addressType", expression = "java(resolver.resolveAddressType(dto.getAddressTypeID()))")
    void updateEntityFromDto(BusinessEntityAddressDto dto, @MappingTarget BusinessEntityAddress entity, @Context EntityResolver resolver);
}


