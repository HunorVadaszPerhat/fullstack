package com.example.backend.mapper.person.personphone;

import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.model.person.personphone.PersonPhone;
import com.example.backend.domain.model.person.personphone.PersonPhoneId;
import com.example.backend.domain.model.person.phonenumbertype.PhoneNumberType;
import com.example.backend.dto.person.personphone.PersonPhoneDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonPhoneMapper {

    private final PersonPhoneResolver resolver;

    public PersonPhoneDto toDto(PersonPhone entity) {
        if (entity == null || entity.getId() == null) return null;

        return PersonPhoneDto.builder()
                .businessEntityId(entity.getId().getBusinessEntityId())
                .phoneNumber(entity.getId().getPhoneNumber())
                .phoneNumberTypeId(entity.getId().getPhoneNumberTypeId())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public PersonPhone toEntity(PersonPhoneDto dto) {
        if (dto == null) return null;

        Person person = resolver.resolvePerson(dto.getBusinessEntityId());
        PhoneNumberType phoneNumberType = resolver.resolvePhoneNumberType(dto.getPhoneNumberTypeId());

        PersonPhoneId id = new PersonPhoneId(
                dto.getBusinessEntityId(),
                dto.getPhoneNumber(),
                dto.getPhoneNumberTypeId()
        );

        return PersonPhone.builder()
                .id(id)
                .person(person)
                .phoneNumberType(phoneNumberType)
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(PersonPhoneDto dto, PersonPhone entity) {
        if (dto == null || entity == null) return;

        if (dto.getBusinessEntityId() != null &&
                dto.getPhoneNumber() != null &&
                dto.getPhoneNumberTypeId() != null) {

            PersonPhoneId id = new PersonPhoneId(
                    dto.getBusinessEntityId(),
                    dto.getPhoneNumber(),
                    dto.getPhoneNumberTypeId()
            );

            entity.setId(id);
            entity.setPerson(resolver.resolvePerson(dto.getBusinessEntityId()));
            entity.setPhoneNumberType(resolver.resolvePhoneNumberType(dto.getPhoneNumberTypeId()));
        }

        // ModifiedDate is handled automatically by @UpdateTimestamp
    }
}

