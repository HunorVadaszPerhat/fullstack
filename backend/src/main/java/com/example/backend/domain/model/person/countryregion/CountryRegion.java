package com.example.backend.domain.model.person.countryregion;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * Lookup table containing the ISO standard codes for countries and regions.
 */
@Entity
@Table(
        name = "CountryRegion",
        uniqueConstraints = @UniqueConstraint(name = "AK_CountryRegion_Name", columnNames = "Name"),
        schema = "Person"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryRegion {

    /**
     * ISO standard code for countries and regions.
     * Clustered index created by a primary key constraint.
     */
    @Id
    @Column(name = "CountryRegionCode", nullable = false, length = 3)
    private String countryRegionCode;

    /**
     * Country or region name.
     */
    @Column(name = "Name", nullable = false, length = 50, unique = true)
    private String name;

    /**
     * Date and time the record was last updated.
     * Automatically set when the entity is first created.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ModifiedDate", nullable = false, columnDefinition = "datetime default getdate()")
    private Date modifiedDate;
}

