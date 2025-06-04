package com.example.backend.domain.model.person.address;

import com.example.backend.domain.model.person.stateprovince.StateProvince;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Address", schema = "Person")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @Column(name="AddressID")
    @NotNull(message="Address entity ID is required")
    private Integer addressId;

    @NotNull(message="Address Line 1 is required")
    @Column(name = "AddressLine1", nullable = false, length = 60)
    private String addressLine1;

    @Column(name = "AddressLine2", length = 60)
    private String addressLine2;

    @NotNull(message="City is required")
    @Column(name = "City", nullable = false, length = 60)
    private String city;

    @ManyToOne
    @JoinColumn(name="StateProvinceID", nullable = false)
    private StateProvince stateProvince;

    @NotNull(message="")
    @Column(name="PostalCode", nullable = false, length = 15)
    private String postalCode;

    /*
    * NOTE: Hibernate Spatial (with JTS or GEOLatte)
    * */
    @Column(name = "SpatialLocation")
    private Object spatialLocation;

    @Column(name = "rowguid", nullable = false, columnDefinition = "uniqueidentifier default newid()")
    private UUID rowguid;

    @Column(name = "ModifiedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
}
