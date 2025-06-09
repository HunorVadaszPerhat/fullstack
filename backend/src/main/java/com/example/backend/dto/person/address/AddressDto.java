package com.example.backend.dto.person.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;
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

    /**
     * Primary key for Address.
     */
    private Integer addressId;

    /**
     * First street address line.
     */
    private String addressLine1;

    /**
     * Second street address line.
     */
    private String addressLine2;

    /**
     * City name.
     */
    private String city;

    /**
     * ID of the State or Province (instead of the full object).
     */
    private Integer stateProvinceId;

    /**
     * Postal code.
     */
    private String postalCode;

    /**
     * Spatial/geographic location (optional: refine type based on use case).
     */
    @Schema(hidden = true)
    private Object spatialLocation;

    /**
     * Unique identifier for this address.
     */
    @Schema(hidden = true)
    private UUID rowguid;

    /**
     * Last modified timestamp.
     */
    private Date modifiedDate;
}

