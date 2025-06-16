package com.example.backend.dto.humanresources.employeepayhistory;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayHistoryDto {
    private Integer businessEntityId;
    private LocalDateTime rateChangeDate;
    private BigDecimal rate;
    private Integer payFrequency;
    private LocalDateTime modifiedDate;
}
