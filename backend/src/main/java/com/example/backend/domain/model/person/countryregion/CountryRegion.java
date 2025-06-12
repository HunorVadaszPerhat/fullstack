package com.example.backend.domain.model.person.countryregion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Lookup table containing the ISO standard codes for countries and regions.
 */
@Entity
@Table(name = "CountryRegion", schema = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryRegion {
    @Id
    @Column(name = "CountryRegionCode", nullable = false, length = 3)
    private String countryRegionCode;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}