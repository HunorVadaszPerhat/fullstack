package com.example.backend.dto.production.location;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {
    private Short locationId;
    private String name;
    private BigDecimal costRate;
    private BigDecimal availability;
    private LocalDateTime modifiedDate;
}

