package com.example.backend.mapper.person.businessentity;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.dto.person.businessentity.BusinessEntityDto;
import org.springframework.stereotype.Component;

@Component
public class BusinessEntityMapper {

    public BusinessEntityDto toDto(BusinessEntity entity) {
        if (entity == null) return null;

        return BusinessEntityDto.builder()
                .businessEntityId(entity.getBusinessEntityId())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public BusinessEntity toEntity(BusinessEntityDto dto) {
        if (dto == null) return null;

        return BusinessEntity.builder()
                .businessEntityId(dto.getBusinessEntityId()) // often ignored for auto-gen inserts
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(BusinessEntityDto dto, BusinessEntity entity) {
        if (dto == null || entity == null) return;

        if (dto.getRowguid() != null) entity.setRowguid(dto.getRowguid());
        // Usually skip setting modifiedDate — it's managed by @UpdateTimestamp
    }
}

