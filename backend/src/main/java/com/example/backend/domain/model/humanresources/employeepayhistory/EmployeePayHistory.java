package com.example.backend.domain.model.humanresources.employeepayhistory;

import com.example.backend.domain.model.humanresources.employee.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "EmployeePayHistory", schema = "HumanResources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayHistory {

    @EmbeddedId
    private EmployeePayHistoryId id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("businessEntityId")
    @JoinColumn(name = "BusinessEntityID", nullable = false)
    private Employee employee;

    @Column(name = "Rate", nullable = false)
    private BigDecimal rate;

    @Column(name = "PayFrequency", nullable = false)
    private Integer payFrequency;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}