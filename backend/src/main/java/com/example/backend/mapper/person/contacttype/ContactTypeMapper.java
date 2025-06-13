package com.example.backend.mapper.person.contacttype;

import com.example.backend.domain.model.person.contacttype.ContactType;
import com.example.backend.dto.person.contacttype.ContactTypeDto;
import org.springframework.stereotype.Component;

@Component
public class ContactTypeMapper {

    public ContactTypeDto toDto(ContactType entity) {
        if (entity == null) return null;

        return ContactTypeDto.builder()
                .contactTypeId(entity.getContactTypeId())
                .name(entity.getName())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public ContactType toEntity(ContactTypeDto dto) {
        if (dto == null) return null;

        return ContactType.builder()
                .contactTypeId(dto.getContactTypeId()) // optional for updates
                .name(dto.getName())
                .modifiedDate(dto.getModifiedDate())   // @UpdateTimestamp usually handles this
                .build();
    }

    public void updateEntityFromDto(ContactTypeDto dto, ContactType entity) {
        if (dto == null || entity == null) return;

        if (dto.getName() != null) entity.setName(dto.getName());
        // Avoid setting modifiedDate manually — it's handled by @UpdateTimestamp
    }
}

