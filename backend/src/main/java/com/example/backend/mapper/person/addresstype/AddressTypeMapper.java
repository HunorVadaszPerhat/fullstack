package com.example.backend.mapper.person.addresstype;

import com.example.backend.domain.model.person.addresstype.AddressType;
import com.example.backend.dto.person.addresstype.AddressTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class AddressTypeMapper {
    public abstract AddressTypeDto toDto(AddressType addressType);
    public abstract AddressType toEntity(AddressTypeDto addressTypeDto);
    public abstract void updateEntityFromDto(AddressTypeDto dto, @MappingTarget AddressType entity);

}
