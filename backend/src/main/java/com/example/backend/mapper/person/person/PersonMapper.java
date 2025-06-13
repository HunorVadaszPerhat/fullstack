package com.example.backend.mapper.person.person;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.repository.person.businessentity.BusinessEntityRepository;
import com.example.backend.dto.person.person.PersonDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PersonMapper {

    private final BusinessEntityRepository businessEntityRepository;

    public PersonDto toDto(Person entity) {
        if (entity == null) return null;

        return PersonDto.builder()
                .businessEntityId(entity.getBusinessEntity() != null ? entity.getBusinessEntity().getBusinessEntityId() : null)
                .personType(entity.getPersonType())
                .nameStyle(entity.getNameStyle())
                .title(entity.getTitle())
                .firstName(entity.getFirstName())
                .middleName(entity.getMiddleName())
                .lastName(entity.getLastName())
                .suffix(entity.getSuffix())
                .emailPromotion(entity.getEmailPromotion())
                .additionalContactInfo(entity.getAdditionalContactInfo())
                .demographics(entity.getDemographics())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public Person toEntity(PersonDto dto) {
        if (dto == null) return null;

        BusinessEntity businessEntity = resolveBusinessEntity(dto.getBusinessEntityId());

        return Person.builder()
                .businessEntityId(dto.getBusinessEntityId())
                .businessEntity(businessEntity)
                .personType(dto.getPersonType())
                .nameStyle(dto.getNameStyle())
                .title(dto.getTitle())
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .suffix(dto.getSuffix())
                .emailPromotion(dto.getEmailPromotion())
                .additionalContactInfo(dto.getAdditionalContactInfo())
                .demographics(dto.getDemographics())
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(PersonDto dto, Person entity) {
        if (dto == null || entity == null) return;

        if (dto.getPersonType() != null) entity.setPersonType(dto.getPersonType());
        if (dto.getNameStyle() != null) entity.setNameStyle(dto.getNameStyle());
        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getFirstName() != null) entity.setFirstName(dto.getFirstName());
        if (dto.getMiddleName() != null) entity.setMiddleName(dto.getMiddleName());
        if (dto.getLastName() != null) entity.setLastName(dto.getLastName());
        if (dto.getSuffix() != null) entity.setSuffix(dto.getSuffix());
        if (dto.getEmailPromotion() != null) entity.setEmailPromotion(dto.getEmailPromotion());
        if (dto.getAdditionalContactInfo() != null) entity.setAdditionalContactInfo(dto.getAdditionalContactInfo());
        if (dto.getDemographics() != null) entity.setDemographics(dto.getDemographics());
        if (dto.getRowguid() != null) entity.setRowguid(dto.getRowguid());

        if (dto.getBusinessEntityId() != null) {
            BusinessEntity businessEntity = resolveBusinessEntity(dto.getBusinessEntityId());
            entity.setBusinessEntity(businessEntity);
            entity.setBusinessEntityId(dto.getBusinessEntityId()); // keeps both ID and reference in sync
        }
    }

    private BusinessEntity resolveBusinessEntity(Integer id) {
        if (id == null) throw new IllegalArgumentException("BusinessEntityId must not be null");

        return businessEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found with ID: " + id));
    }
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
