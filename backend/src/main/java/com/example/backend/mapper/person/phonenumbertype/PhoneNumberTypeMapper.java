package com.example.backend.mapper.person.phonenumbertype;

import com.example.backend.domain.model.person.phonenumbertype.PhoneNumberType;
import com.example.backend.dto.person.phonenumbertype.PhoneNumberTypeDto;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberTypeMapper {

    public PhoneNumberTypeDto toDto(PhoneNumberType entity) {
        if (entity == null) return null;

        return PhoneNumberTypeDto.builder()
                .phoneNumberTypeId(entity.getPhoneNumberTypeId())
                .name(entity.getName())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public PhoneNumberType toEntity(PhoneNumberTypeDto dto) {
        if (dto == null) return null;

        return PhoneNumberType.builder()
                .phoneNumberTypeId(dto.getPhoneNumberTypeId()) // optional, depending on insert/update use
                .name(dto.getName())
                .modifiedDate(dto.getModifiedDate()) // usually ignored due to @UpdateTimestamp
                .build();
    }

    public void updateEntityFromDto(PhoneNumberTypeDto dto, PhoneNumberType entity) {
        if (dto == null || entity == null) return;

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }

        // Leave modifiedDate alone — it's managed by JPA @UpdateTimestamp
    }
}

