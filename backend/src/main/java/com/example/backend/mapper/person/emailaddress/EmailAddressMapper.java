package com.example.backend.mapper.person.emailaddress;

import com.example.backend.domain.model.person.emailaddress.EmailAddress;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.repository.person.person.PersonRepository;
import com.example.backend.dto.person.emailaddress.EmailAddressDto;
import com.example.backend.dto.person.emailaddress.EmailAddressIdDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EmailAddressMapper {

    private final PersonRepository personRepository;

    public EmailAddressDto toDto(EmailAddress entity) {
        if (entity == null) return null;

        return EmailAddressDto.builder()
                .id(new EmailAddressIdDto(
                        entity.getId().getBusinessEntityId(),
                        entity.getId().getEmailAddressId()
                ))
                .personId(entity.getPerson() != null ? entity.getPerson().getBusinessEntityId() : null)
                .emailAddress(entity.getEmailAddress())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public EmailAddress toEntity(EmailAddressDto dto) {
        if (dto == null) return null;

        Person person = resolvePerson(dto.getPersonId());

        return EmailAddress.builder()
                .id(dto.getId() != null ? dto.getId().toEntity() : null)
                .person(person)
                .emailAddress(dto.getEmailAddress())
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(EmailAddressDto dto, EmailAddress entity) {
        if (dto == null || entity == null) return;

        if (dto.getEmailAddress() != null) entity.setEmailAddress(dto.getEmailAddress());
        if (dto.getRowguid() != null) entity.setRowguid(dto.getRowguid());
        if (dto.getPersonId() != null) {
            Person person = resolvePerson(dto.getPersonId());
            entity.setPerson(person);
        }
    }

    private Person resolvePerson(Integer id) {
        if (id == null) throw new IllegalArgumentException("Person ID cannot be null");
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with ID: " + id));
    }
}
