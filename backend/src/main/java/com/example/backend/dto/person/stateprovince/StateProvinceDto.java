package com.example.backend.dto.person.stateprovince;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateProvinceDto {
    private Integer stateProvinceId;
    private String stateProvinceCode;
    private String countryRegionCode;
    private Boolean isOnlyStateProvinceFlag;
    private String name;
    private Integer territoryId;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}

