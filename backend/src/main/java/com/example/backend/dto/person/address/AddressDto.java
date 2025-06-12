package com.example.backend.dto.person.address;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Address entity.
 * Contains simplified address information for external communication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private Integer addressId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private Integer stateProvinceId;
    private String postalCode;
    private Object spatialLocation;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}

