package com.example.backend.domain.model.humanresources.employeepayhistory;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmployeePayHistoryId implements Serializable {

    @Column(name = "BusinessEntityID", nullable = false)
    private Integer businessEntityId;

    @Column(name = "RateChangeDate", nullable = false)
    private LocalDateTime rateChangeDate;
}
