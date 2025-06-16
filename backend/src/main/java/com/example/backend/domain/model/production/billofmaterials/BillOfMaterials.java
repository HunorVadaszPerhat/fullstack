package com.example.backend.domain.model.production.billofmaterials;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "BillOfMaterials", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillOfMaterials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BillOfMaterialsID")
    private Integer billOfMaterialsId;

    @ManyToOne
    @JoinColumn(name = "ProductAssemblyID")
    private Product productAssembly;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ComponentID", nullable = false)
    private Product component;

    @Column(name = "StartDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "EndDate")
    private LocalDateTime endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "UnitMeasureCode", nullable = false)
    private UnitMeasure unitMeasure;

    @Column(name = "BOMLevel", nullable = false)
    private Short bomLevel;

    @Column(name = "PerAssemblyQty", nullable = false, precision = 8, scale = 2)
    private BigDecimal perAssemblyQty;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}
