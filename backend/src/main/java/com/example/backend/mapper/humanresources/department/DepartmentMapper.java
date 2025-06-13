package com.example.backend.mapper.humanresources.department;


import com.example.backend.domain.model.humanresources.department.Department;
import com.example.backend.dto.humanresources.department.DepartmentDto;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentDto toDto(Department entity) {
        if (entity == null) return null;
        return DepartmentDto.builder()
                .departmentId(entity.getDepartmentId())
                .name(entity.getName())
                .groupName(entity.getGroupName())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public Department toEntity(DepartmentDto dto) {
        if (dto == null) return null;
        return Department.builder()
                .departmentId(dto.getDepartmentId()) // nullable
                .name(dto.getName())
                .groupName(dto.getGroupName())
                .build();
    }

    public void updateFromDto(DepartmentDto dto, Department entity) {
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getGroupName() != null) entity.setGroupName(dto.getGroupName());
    }
}
