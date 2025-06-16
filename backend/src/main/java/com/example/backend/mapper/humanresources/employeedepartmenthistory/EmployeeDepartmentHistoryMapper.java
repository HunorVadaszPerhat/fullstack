package com.example.backend.mapper.humanresources.employeedepartmenthistory;

import com.example.backend.domain.model.humanresources.employeedepartmenthistory.EmployeeDepartmentHistory;
import com.example.backend.domain.model.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryId;
import com.example.backend.dto.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDepartmentHistoryMapper {

    public EmployeeDepartmentHistoryDto toDto(EmployeeDepartmentHistory entity) {
        if (entity == null) return null;

        return EmployeeDepartmentHistoryDto.builder()
                .businessEntityId(entity.getId().getBusinessEntityId())
                .startDate(entity.getId().getStartDate())
                .departmentId(entity.getId().getDepartmentId())
                .shiftId(entity.getId().getShiftId())
                .endDate(entity.getEndDate())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public EmployeeDepartmentHistory toEntity(EmployeeDepartmentHistoryDto dto, EmployeeDepartmentHistoryResolver resolver) {
        if (dto == null) return null;

        EmployeeDepartmentHistory entity = new EmployeeDepartmentHistory();

        EmployeeDepartmentHistoryId id = new EmployeeDepartmentHistoryId(
                dto.getBusinessEntityId(),
                dto.getStartDate(),
                dto.getDepartmentId(),
                dto.getShiftId()
        );

        entity.setId(id);
        entity.setEmployee(resolver.resolveEmployee(dto.getBusinessEntityId()));
        entity.setDepartment(resolver.resolveDepartment(dto.getDepartmentId()));
        entity.setShift(resolver.resolveShift(dto.getShiftId()));
        entity.setEndDate(dto.getEndDate());

        return entity;
    }

    public void updateEntityFromDto(EmployeeDepartmentHistoryDto dto, EmployeeDepartmentHistory entity, EmployeeDepartmentHistoryResolver resolver) {
        entity.setEmployee(resolver.resolveEmployee(dto.getBusinessEntityId()));
        entity.setDepartment(resolver.resolveDepartment(dto.getDepartmentId()));
        entity.setShift(resolver.resolveShift(dto.getShiftId()));
        entity.setEndDate(dto.getEndDate());
    }
}
