package com.example.backend.dto.humanresources.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO for Employee")
public class EmployeeDto {

    private Integer businessEntityId;
    private String nationalIdNumber;
    private String loginId;
    private String organizationNode;
    private Integer organizationLevel;
    private String jobTitle;
    private LocalDate birthDate;
    private String maritalStatus;
    private String gender;
    private LocalDate hireDate;
    private Boolean salariedFlag;
    private Short vacationHours;
    private Short sickLeaveHours;
    private Boolean currentFlag;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}

