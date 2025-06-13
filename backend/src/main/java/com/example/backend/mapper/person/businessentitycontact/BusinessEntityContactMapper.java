package com.example.backend.mapper.person.businessentitycontact;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.model.person.businessentitycontact.BusinessEntityContact;
import com.example.backend.domain.model.person.businessentitycontact.BusinessEntityContactId;
import com.example.backend.domain.model.person.contacttype.ContactType;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.dto.person.businessentitycontact.BusinessEntityContactDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessEntityContactMapper {

    private final BusinessEntityContactResolver resolver;

    public BusinessEntityContactDto toDto(BusinessEntityContact entity) {
        if (entity == null || entity.getId() == null) return null;

        return BusinessEntityContactDto.builder()
                .businessEntityId(entity.getId().getBusinessEntityId())
                .personId(entity.getId().getPersonId())
                .contactTypeId(entity.getId().getContactTypeId())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public BusinessEntityContact toEntity(BusinessEntityContactDto dto) {
        if (dto == null) return null;

        BusinessEntity businessEntity = resolver.resolveBusinessEntity(dto.getBusinessEntityId());
        Person person = resolver.resolvePerson(dto.getPersonId());
        ContactType contactType = resolver.resolveContactType(dto.getContactTypeId());

        return BusinessEntityContact.builder()
                .id(new BusinessEntityContactId(dto.getBusinessEntityId(), dto.getPersonId(), dto.getContactTypeId()))
                .businessEntity(businessEntity)
                .person(person)
                .contactType(contactType)
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(BusinessEntityContactDto dto, BusinessEntityContact entity) {
        if (dto == null || entity == null) return;

        if (dto.getBusinessEntityId() != null && dto.getPersonId() != null && dto.getContactTypeId() != null) {
            entity.setId(new BusinessEntityContactId(dto.getBusinessEntityId(), dto.getPersonId(), dto.getContactTypeId()));
            entity.setBusinessEntity(resolver.resolveBusinessEntity(dto.getBusinessEntityId()));
            entity.setPerson(resolver.resolvePerson(dto.getPersonId()));
            entity.setContactType(resolver.resolveContactType(dto.getContactTypeId()));
        }

        if (dto.getRowguid() != null) {
            entity.setRowguid(dto.getRowguid());
        }
    }
}