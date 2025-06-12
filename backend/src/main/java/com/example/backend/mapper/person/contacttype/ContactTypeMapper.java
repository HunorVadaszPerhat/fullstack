package com.example.backend.mapper.person.contacttype;

import com.example.backend.domain.model.person.contacttype.ContactType;
import com.example.backend.dto.person.contacttype.ContactTypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactTypeMapper {
    ContactTypeDto toDto(ContactType entity);
    ContactType toEntity(ContactTypeDto dto);
}

