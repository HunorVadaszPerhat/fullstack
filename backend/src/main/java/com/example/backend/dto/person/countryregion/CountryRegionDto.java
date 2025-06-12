package com.example.backend.dto.person.countryregion;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryRegionDto {
    private String countryRegionCode;
    private String name;
    private LocalDateTime modifiedDate;
}