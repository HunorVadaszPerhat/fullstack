package com.example.backend.dto.humanresources.employeedepartmenthistory;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDepartmentHistoryDto {
    private Integer businessEntityId;
    private Short departmentId;
    private Byte shiftId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime modifiedDate;
}
