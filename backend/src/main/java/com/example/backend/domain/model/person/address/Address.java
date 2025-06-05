package com.example.backend.domain.model.person.address;

import com.example.backend.domain.model.person.stateprovince.StateProvince;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;

import java.util.Date;
import java.util.UUID;

/**
 * Street address information for customers, employees, and vendors.
 */
@Entity
@Table(name = "Address",
        indexes = {
                @Index(name = "AK_Address_rowguid", columnList = "rowguid", unique = true),
                @Index(name = "IX_Address_StateProvinceID", columnList = "StateProvinceID"),
                @Index(
                        name = "IX_Address_AddressLine1_AddressLine2_City_StateProvinceID_PostalCode",
                        columnList = "AddressLine1, AddressLine2, City, StateProvinceID, PostalCode"
                )
        },
        schema = "Person"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    /**
     * Primary key for Address records.
     * Auto-incremented.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AddressID", nullable = false)
    private Integer addressId;

    /**
     * First street address line.
     */
    @Column(name = "AddressLine1", nullable = false, length = 60)
    private String addressLine1;

    /**
     * Second street address line.
     */
    @Column(name = "AddressLine2", length = 60)
    private String addressLine2;

    /**
     * Name of the city.
     */
    @Column(name = "City", nullable = false, length = 30)
    private String city;

    /**
     * Unique identification number for the state or province.
     * Foreign key to StateProvinceRepository table.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "StateProvinceID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_Address_StateProvince_StateProvinceID"))
    private StateProvince stateProvince;

    /**
     * Postal code for the street address.
     */
    @Column(name = "PostalCode", nullable = false, length = 15)
    private String postalCode;

    /**
     * Latitude and longitude of this address.
     * Type: SQL Server geography.
     * You may need a custom type handler or converter for this.
     */
    @Column(name = "SpatialLocation", columnDefinition = "geography")
    private Object spatialLocation; // Consider replacing with a custom Geography class or converter

    /**
     * ROWGUIDCOL number uniquely identifying the record.
     * Used to support a merge replication sample.
     */
    @Column(name = "rowguid", nullable = false, columnDefinition = "uniqueidentifier default newid()")
    @Generated(event = EventType.INSERT)
    private UUID rowguid;

    /**
     * Date and time the record was last updated.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ModifiedDate", nullable = false, columnDefinition = "datetime default getdate()")
    private Date modifiedDate;
}
