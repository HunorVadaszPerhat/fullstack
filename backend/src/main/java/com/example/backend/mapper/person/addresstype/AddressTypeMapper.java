package com.example.backend.mapper.person.addresstype;

import com.example.backend.domain.model.person.addresstype.AddressType;
import com.example.backend.dto.person.addresstype.AddressTypeDto;
import org.springframework.stereotype.Component;

@Component
public class AddressTypeMapper {

    public AddressTypeDto toDto(AddressType entity) {
        if (entity == null) return null;

        return AddressTypeDto.builder()
                .addressTypeId(entity.getAddressTypeId())
                .name(entity.getName())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public AddressType toEntity(AddressTypeDto dto) {
        if (dto == null) return null;

        return AddressType.builder()
                .addressTypeId(dto.getAddressTypeId()) // optional for new inserts
                .name(dto.getName())
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(AddressTypeDto dto, AddressType entity) {
        if (dto == null || entity == null) return;

        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getRowguid() != null) entity.setRowguid(dto.getRowguid());
        // Normally, you do NOT update `modifiedDate` manually — it's handled by @UpdateTimestamp
    }
}
