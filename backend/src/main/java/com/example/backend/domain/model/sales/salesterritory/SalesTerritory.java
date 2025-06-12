package com.example.backend.domain.model.sales.salesterritory;

import com.example.backend.domain.model.person.countryregion.CountryRegion;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Sales territory lookup table.
 */
@Entity
@Table(name = "SalesTerritory", schema = "Sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesTerritory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TerritoryID", nullable = false)
    private Integer territoryId;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "CountryRegionCode", nullable = false)
    private CountryRegion countryRegion;

    @Column(name = "[Group]", nullable = false, length = 50)
    private String group;

    @Column(name = "SalesYTD", nullable = false)
    private BigDecimal salesYTD = BigDecimal.ZERO;

    @Column(name = "SalesLastYear", nullable = false)
    private BigDecimal salesLastYear = BigDecimal.ZERO;

    @Column(name = "CostYTD", nullable = false)
    private BigDecimal costYTD = BigDecimal.ZERO;

    @Column(name = "CostLastYear", nullable = false)
    private BigDecimal costLastYear = BigDecimal.ZERO;

    @Column(name = "rowguid", nullable = false)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}