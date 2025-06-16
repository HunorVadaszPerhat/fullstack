package com.example.backend.domain.model.humanresources.employeedepartmenthistory;

import com.example.backend.domain.model.humanresources.department.Department;
import com.example.backend.domain.model.humanresources.employee.Employee;
import com.example.backend.domain.model.humanresources.shift.Shift;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "EmployeeDepartmentHistory", schema = "HumanResources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDepartmentHistory {

    @EmbeddedId
    private EmployeeDepartmentHistoryId id;

    @ManyToOne
    @MapsId("businessEntityId")
    @JoinColumn(name = "BusinessEntityID", nullable = false)
    private Employee employee;

    @ManyToOne
    @MapsId("departmentId")
    @JoinColumn(name = "DepartmentID", nullable = false)
    private Department department;

    @ManyToOne
    @MapsId("shiftId")
    @JoinColumn(name = "ShiftID", nullable = false)
    private Shift shift;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

