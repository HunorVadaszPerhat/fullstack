package com.example.backend.domain.model.humanresources.employeedepartmenthistory;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmployeeDepartmentHistoryId implements Serializable {

    @Column(name = "BusinessEntityID")
    private Integer businessEntityId;

    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "DepartmentID")
    private Short departmentId;

    @Column(name = "ShiftID")
    private Byte shiftId;
}

