package com.example.backend.mapper.person.phonenumbertype;

import com.example.backend.domain.model.person.phonenumbertype.PhoneNumberType;
import com.example.backend.dto.person.phonenumbertype.PhoneNumberTypeDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PhoneNumberTypeMapper {
    PhoneNumberTypeDto toDto(PhoneNumberType entity);
    PhoneNumberType toEntity(PhoneNumberTypeDto dto);
    void updateEntityFromDto(PhoneNumberTypeDto dto, @MappingTarget PhoneNumberType entity);
}

