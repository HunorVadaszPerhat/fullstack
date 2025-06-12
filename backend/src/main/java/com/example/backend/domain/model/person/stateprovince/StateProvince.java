package com.example.backend.domain.model.person.stateprovince;

import com.example.backend.domain.model.person.countryregion.CountryRegion;
import com.example.backend.domain.model.sales.salesterritory.SalesTerritory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * State and province lookup table.
 */
@Entity
@Table(name = "StateProvince", schema = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateProvince {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StateProvinceID", nullable = false)
    private Integer stateProvinceId;

    @Column(name = "StateProvinceCode", nullable = false, length = 3)
    private String stateProvinceCode;

    @ManyToOne
    @JoinColumn(name = "CountryRegionCode", nullable = false)
    private CountryRegion countryRegion;

    @Column(name = "IsOnlyStateProvinceFlag", nullable = false)
    private Boolean isOnlyStateProvinceFlag = true;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "TerritoryID", nullable = false)
    private SalesTerritory salesTerritory;

    @Column(name = "rowguid", nullable = false)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}