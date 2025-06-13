package com.example.backend.mapper.person.password;

import com.example.backend.domain.model.person.password.Password;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.repository.person.person.PersonRepository;
import com.example.backend.dto.person.password.PasswordDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PasswordMapper {

    private final PersonRepository personRepository;

    public PasswordDto toDto(Password entity) {
        if (entity == null) return null;

        return PasswordDto.builder()
                .personId(entity.getPerson() != null ? entity.getPerson().getBusinessEntityId() : null)
                .passwordHash(entity.getPasswordHash())
                .passwordSalt(entity.getPasswordSalt())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public Password toEntity(PasswordDto dto) {
        if (dto == null) return null;

        Person person = resolvePerson(dto.getPersonId());

        return Password.builder()
                .businessEntityId(dto.getPersonId()) // maps to the same value as person.getId()
                .person(person)
                .passwordHash(dto.getPasswordHash())
                .passwordSalt(dto.getPasswordSalt())
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(PasswordDto dto, Password entity) {
        if (dto == null || entity == null) return;

        if (dto.getPasswordHash() != null) entity.setPasswordHash(dto.getPasswordHash());
        if (dto.getPasswordSalt() != null) entity.setPasswordSalt(dto.getPasswordSalt());
        if (dto.getRowguid() != null) entity.setRowguid(dto.getRowguid());

        if (dto.getPersonId() != null) {
            Person person = resolvePerson(dto.getPersonId());
            entity.setPerson(person);
            entity.setBusinessEntityId(person.getBusinessEntityId()); // keep IDs consistent
        }
    }

    private Person resolvePerson(Integer personId) {
        if (personId == null) {
            throw new IllegalArgumentException("Person ID must not be null");
        }

        return personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with ID: " + personId));
    }
}
