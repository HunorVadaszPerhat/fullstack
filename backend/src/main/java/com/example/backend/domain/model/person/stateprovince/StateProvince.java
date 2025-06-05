package com.example.backend.domain.model.person.stateprovince;

import com.example.backend.domain.model.person.countryregion.CountryRegion;
import com.example.backend.domain.model.sales.salesterritory.SalesTerritory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

/**
 * State and province lookup table.
 */
@Entity
@Table(
        name = "StateProvince",
        schema = "Person",
        uniqueConstraints = {
                @UniqueConstraint(name = "AK_StateProvince_Name", columnNames = "Name"),
                @UniqueConstraint(name = "AK_StateProvince_StateProvinceCode_CountryRegionCode", columnNames = {"StateProvinceCode", "CountryRegionCode"}),
                @UniqueConstraint(name = "AK_StateProvince_rowguid", columnNames = "rowguid")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateProvince {

    /**
     * Primary key for StateProvinceRepository records.
     * Clustered index created by a primary key constraint.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StateProvinceID")
    private Integer stateProvinceId;

    /**
     * ISO standard state or province code.
     */
    @Column(name = "StateProvinceCode", nullable = false, length = 3)
    private String stateProvinceCode;

    /**
     * ISO standard country or region code. Foreign key to CountryRegion.CountryRegionCode.
     */
    @ManyToOne
    @JoinColumn(name = "CountryRegionCode", nullable = false)
    private CountryRegion countryRegion;

    /**
     * 0 = StateProvinceCode exists. 1 = StateProvinceCode unavailable, using CountryRegionCode.
     */
    @Column(name = "IsOnlyStateProvinceFlag", nullable = false)
    private Boolean isOnlyStateProvinceFlag = true;

    /**
     * State or province description.
     */
    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    /**
     * ID of the territory in which the state or province is located.
     * Foreign key to SalesTerritory.TerritoryID.
     */
    @ManyToOne
    @JoinColumn(name = "TerritoryID", nullable = false)
    private SalesTerritory salesTerritory;

    /**
     * ROWGUIDCOL number uniquely identifying the record.
     * Used to support a merge replication sample.
     */
    @Column(name = "rowguid", nullable = false, columnDefinition = "uniqueidentifier default newid()", unique = true)
    private UUID rowguid = UUID.randomUUID();

    /**
     * Date and time the record was last updated.
     * Automatically set when the entity is first created.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ModifiedDate", nullable = false, columnDefinition = "datetime default getdate()")
    private Date modifiedDate;
}


