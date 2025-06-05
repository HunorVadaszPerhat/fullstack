package com.example.backend.mapper.person.address;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.dto.person.address.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(source = "stateProvince.stateProvinceId", target = "stateProvinceId")
    AddressDto toDto(Address address);

    @Mapping(source = "stateProvinceId", target = "stateProvince.stateProvinceId")
    Address toEntity(AddressDto addressDto);
}
