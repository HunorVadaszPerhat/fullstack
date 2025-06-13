package com.example.backend.domain.model.humanresources.employee;

import com.example.backend.domain.model.person.person.Person;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Employee", schema = "HumanResources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @Column(name = "BusinessEntityID", nullable = false)
    private Integer businessEntityId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "BusinessEntityID", referencedColumnName = "BusinessEntityID", nullable = false)
    private Person person;

    @Column(name = "NationalIDNumber", nullable = false, length = 15)
    private String nationalIdNumber;

    @Column(name = "LoginID", nullable = false, length = 256)
    private String loginId;

    @Column(name = "OrganizationNode")
    private String organizationNode; // Will be stored as String for hierarchyid

    @Column(name = "OrganizationLevel", insertable = false, updatable = false)
    private Integer organizationLevel;

    @Column(name = "JobTitle", nullable = false, length = 50)
    private String jobTitle;

    @Column(name = "BirthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "MaritalStatus", nullable = false, length = 1)
    private String maritalStatus;

    @Column(name = "Gender", nullable = false, length = 1)
    private String gender;

    @Column(name = "HireDate", nullable = false)
    private LocalDate hireDate;

    @Column(name = "SalariedFlag", nullable = false)
    private Boolean salariedFlag;

    @Column(name = "VacationHours", nullable = false)
    private Short vacationHours;

    @Column(name = "SickLeaveHours", nullable = false)
    private Short sickLeaveHours;

    @Column(name = "CurrentFlag", nullable = false)
    private Boolean currentFlag;

    @Column(name = "rowguid", nullable = false)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

