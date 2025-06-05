package com.example.backend.mapper.person.person;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.dto.person.person.PersonDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(source = "businessEntity.businessEntityId", target = "businessEntityId")
    PersonDto toDto(Person person);

    @Mapping(source = "businessEntityId", target = "businessEntity.businessEntityId")
    Person toEntity(PersonDto dto);

    // Optional update method for PATCH/PUT
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PersonDto dto, @MappingTarget Person entity);
}

/* EXPLICIT MANUAL MAPPING:
------------------------------------------------------------------------------------------------------------------------

public PersonDto toDtoCustom(Person person) {
    if (person == null) {
        return null;
    }

    PersonDto dto = new PersonDto();

    // Map nested field: from Person.businessEntity.businessEntityID → PersonDto.businessEntityId
    // @Mapping(source = "businessEntity.businessEntityID", target = "businessEntityId")
    if (person.getBusinessEntity() != null) {
        dto.setBusinessEntityId(person.getBusinessEntity().getBusinessEntityID());
    }

    // All mappings below follow this pattern:
    // Get value from source (Person), set on target (PersonDto)

    // @Mapping(source = "personType", target = "personType")
    dto.setPersonType(person.getPersonType());

    // @Mapping(source = "nameStyle", target = "nameStyle")
    dto.setNameStyle(person.getNameStyle());

    // @Mapping(source = "title", target = "title")
    dto.setTitle(person.getTitle());

    // @Mapping(source = "firstName", target = "firstName")
    dto.setFirstName(person.getFirstName());

    // @Mapping(source = "middleName", target = "middleName")
    dto.setMiddleName(person.getMiddleName());

    // @Mapping(source = "lastName", target = "lastName")
    dto.setLastName(person.getLastName());

    // @Mapping(source = "suffix", target = "suffix")
    dto.setSuffix(person.getSuffix());

    // @Mapping(source = "emailPromotion", target = "emailPromotion")
    dto.setEmailPromotion(person.getEmailPromotion());

    // @Mapping(source = "additionalContactInfo", target = "additionalContactInfo")
    dto.setAdditionalContactInfo(person.getAdditionalContactInfo());

    // @Mapping(source = "demographics", target = "demographics")
    dto.setDemographics(person.getDemographics());

    // @Mapping(source = "rowguid", target = "rowguid")
    dto.setRowguid(person.getRowguid());

    // @Mapping(source = "modifiedDate", target = "modifiedDate")
    dto.setModifiedDate(person.getModifiedDate());

    return dto;
}

public Person toEntityCustom(PersonDto dto) {
    if (dto == null) {
        return null;
    }

    Person person = new Person();

    // Reconstruct nested object from ID:
    // @Mapping(source = "businessEntityId", target = "businessEntity.businessEntityID")
    if (dto.getBusinessEntityId() != null) {
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setBusinessEntityID(dto.getBusinessEntityId());
        person.setBusinessEntity(businessEntity);
    }

    // Each line below follows this logic:
    // Get value from source (PersonDto), set it on target (Person)

    // @Mapping(source = "personType", target = "personType")
    person.setPersonType(dto.getPersonType());

    // @Mapping(source = "nameStyle", target = "nameStyle")
    person.setNameStyle(dto.getNameStyle());

    // @Mapping(source = "title", target = "title")
    person.setTitle(dto.getTitle());

    // @Mapping(source = "firstName", target = "firstName")
    person.setFirstName(dto.getFirstName());

    // @Mapping(source = "middleName", target = "middleName")
    person.setMiddleName(dto.getMiddleName());

    // @Mapping(source = "lastName", target = "lastName")
    person.setLastName(dto.getLastName());

    // @Mapping(source = "suffix", target = "suffix")
    person.setSuffix(dto.getSuffix());

    // @Mapping(source = "emailPromotion", target = "emailPromotion")
    person.setEmailPromotion(dto.getEmailPromotion());

    // @Mapping(source = "additionalContactInfo", target = "additionalContactInfo")
    person.setAdditionalContactInfo(dto.getAdditionalContactInfo());

    // @Mapping(source = "demographics", target = "demographics")
    person.setDemographics(dto.getDemographics());

    // @Mapping(source = "rowguid", target = "rowguid")
    person.setRowguid(dto.getRowguid());

    // @Mapping(source = "modifiedDate", target = "modifiedDate")
    person.setModifiedDate(dto.getModifiedDate());

    return person;
}

------------------------------------------------------------------------------------------------------------------------
 */
