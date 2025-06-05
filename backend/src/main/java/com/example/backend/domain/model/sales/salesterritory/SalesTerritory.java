package com.example.backend.domain.model.sales.salesterritory;

import com.example.backend.domain.model.person.countryregion.CountryRegion;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Sales territory lookup table.
 */
@Entity
@Table(
        name = "SalesTerritory",
        schema = "Sales",
        uniqueConstraints = {
                @UniqueConstraint(name = "AK_SalesTerritory_Name", columnNames = "Name"),
                @UniqueConstraint(name = "AK_SalesTerritory_rowguid", columnNames = "rowguid")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesTerritory {

    /**
     * Primary key for SalesTerritory records.
     * Clustered index created by a primary key constraint.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TerritoryID")
    private Integer territoryId;

    /**
     * Sales territory description.
     */
    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    /**
     * ISO standard country or region code. Foreign key to CountryRegion.CountryRegionCode.
     */
    @ManyToOne
    @JoinColumn(name = "CountryRegionCode", nullable = false)
    private CountryRegion countryRegion;

    /**
     * Geographic area to which the sales territory belongs.
     */
    @Column(name = "[Group]", nullable = false, length = 50)
    private String group;

    /**
     * Sales in the territory year to date.
     */
    @Column(name = "SalesYTD", nullable = false, columnDefinition = "money default 0.00")
    private BigDecimal salesYTD = BigDecimal.ZERO;

    /**
     * Sales in the territory the previous year.
     */
    @Column(name = "SalesLastYear", nullable = false, columnDefinition = "money default 0.00")
    private BigDecimal salesLastYear = BigDecimal.ZERO;

    /**
     * Business costs in the territory year to date.
     */
    @Column(name = "CostYTD", nullable = false, columnDefinition = "money default 0.00")
    private BigDecimal costYTD = BigDecimal.ZERO;

    /**
     * Business costs in the territory the previous year.
     */
    @Column(name = "CostLastYear", nullable = false, columnDefinition = "money default 0.00")
    private BigDecimal costLastYear = BigDecimal.ZERO;

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
