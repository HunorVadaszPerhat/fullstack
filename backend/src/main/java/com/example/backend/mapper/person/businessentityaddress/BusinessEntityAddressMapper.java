package com.example.backend.mapper.person.businessentityaddress;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.addresstype.AddressType;
import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.model.person.businessentityaddress.BusinessEntityAddress;
import com.example.backend.domain.model.person.businessentityaddress.BusinessEntityAddressId;
import com.example.backend.dto.person.businessentityaddress.BusinessEntityAddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessEntityAddressMapper {

    private final BusinessEntityAddressResolver resolver;

    public BusinessEntityAddressDto toDto(BusinessEntityAddress entity) {
        if (entity == null) return null;

        return BusinessEntityAddressDto.builder()
                .businessEntityID(entity.getId().getBusinessEntityId())
                .addressID(entity.getId().getAddressId())
                .addressTypeID(entity.getId().getAddressTypeId())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public BusinessEntityAddress toEntity(BusinessEntityAddressDto dto) {
        if (dto == null) return null;

        BusinessEntity businessEntity = resolver.resolveBusinessEntity(dto.getBusinessEntityID());
        Address address = resolver.resolveAddress(dto.getAddressID());
        AddressType addressType = resolver.resolveAddressType(dto.getAddressTypeID());

        return BusinessEntityAddress.builder()
                .id(new BusinessEntityAddressId(dto.getBusinessEntityID(), dto.getAddressID(), dto.getAddressTypeID()))
                .businessEntity(businessEntity)
                .address(address)
                .addressType(addressType)
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(BusinessEntityAddressDto dto, BusinessEntityAddress entity) {
        if (dto == null || entity == null) return;

        if (dto.getRowguid() != null) {
            entity.setRowguid(dto.getRowguid());
        }

        if (dto.getBusinessEntityID() != null && dto.getAddressID() != null && dto.getAddressTypeID() != null) {
            entity.setId(new BusinessEntityAddressId(dto.getBusinessEntityID(), dto.getAddressID(), dto.getAddressTypeID()));
            entity.setBusinessEntity(resolver.resolveBusinessEntity(dto.getBusinessEntityID()));
            entity.setAddress(resolver.resolveAddress(dto.getAddressID()));
            entity.setAddressType(resolver.resolveAddressType(dto.getAddressTypeID()));
        }
    }
}
