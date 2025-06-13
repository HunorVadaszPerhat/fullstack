package com.example.backend.mapper.humanresources.employee;

import com.example.backend.domain.model.humanresources.employee.Employee;
import com.example.backend.dto.humanresources.employee.EmployeeDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDto toDto(Employee entity) {
        return EmployeeDto.builder()
                .businessEntityId(entity.getBusinessEntityId())
                .nationalIdNumber(entity.getNationalIdNumber())
                .loginId(entity.getLoginId())
                .organizationNode(entity.getOrganizationNode())
                .organizationLevel(entity.getOrganizationLevel())
                .jobTitle(entity.getJobTitle())
                .birthDate(entity.getBirthDate())
                .maritalStatus(entity.getMaritalStatus())
                .gender(entity.getGender())
                .hireDate(entity.getHireDate())
                .salariedFlag(entity.getSalariedFlag())
                .vacationHours(entity.getVacationHours())
                .sickLeaveHours(entity.getSickLeaveHours())
                .currentFlag(entity.getCurrentFlag())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public Employee toEntity(EmployeeDto dto) {
        return Employee.builder()
                .businessEntityId(dto.getBusinessEntityId())
                .nationalIdNumber(dto.getNationalIdNumber())
                .loginId(dto.getLoginId())
                .organizationNode(dto.getOrganizationNode())
                .jobTitle(dto.getJobTitle())
                .birthDate(dto.getBirthDate())
                .maritalStatus(dto.getMaritalStatus())
                .gender(dto.getGender())
                .hireDate(dto.getHireDate())
                .salariedFlag(dto.getSalariedFlag())
                .vacationHours(dto.getVacationHours())
                .sickLeaveHours(dto.getSickLeaveHours())
                .currentFlag(dto.getCurrentFlag())
                .rowguid(dto.getRowguid())
                .build();
    }

    public void updateEntityFromDto(EmployeeDto dto, Employee entity) {
        entity.setNationalIdNumber(dto.getNationalIdNumber());
        entity.setLoginId(dto.getLoginId());
        entity.setOrganizationNode(dto.getOrganizationNode());
        entity.setJobTitle(dto.getJobTitle());
        entity.setBirthDate(dto.getBirthDate());
        entity.setMaritalStatus(dto.getMaritalStatus());
        entity.setGender(dto.getGender());
        entity.setHireDate(dto.getHireDate());
        entity.setSalariedFlag(dto.getSalariedFlag());
        entity.setVacationHours(dto.getVacationHours());
        entity.setSickLeaveHours(dto.getSickLeaveHours());
        entity.setCurrentFlag(dto.getCurrentFlag());
        entity.setRowguid(dto.getRowguid());
    }
}

