package com.example.backend.mapper.person.person;

import com.example.backend.domain.model.person.person.Person;
import com.example.backend.dto.person.person.PersonDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(source = "businessEntity.businessEntityID", target = "businessEntityId")
    PersonDto toDto(Person person);

    @Mapping(source = "businessEntityId", target = "businessEntity.businessEntityID")
    Person toEntity(PersonDto dto);

    // Optional update method for PATCH/PUT
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PersonDto dto, @MappingTarget Person entity);
}
