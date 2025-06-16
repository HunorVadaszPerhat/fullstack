package com.example.backend.domain.model.production.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Product", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID", nullable = false)
    private Integer productId;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @Column(name = "ProductNumber", nullable = false, length = 25)
    private String productNumber;

    @Column(name = "MakeFlag", nullable = false)
    private Boolean makeFlag = true;

    @Column(name = "FinishedGoodsFlag", nullable = false)
    private Boolean finishedGoodsFlag = true;

    @Column(name = "Color", length = 15)
    private String color;

    @Column(name = "SafetyStockLevel", nullable = false)
    private Short safetyStockLevel;

    @Column(name = "ReorderPoint", nullable = false)
    private Short reorderPoint;

    @Column(name = "StandardCost", nullable = false)
    private BigDecimal standardCost;

    @Column(name = "ListPrice", nullable = false)
    private BigDecimal listPrice;

    @Column(name = "Size", length = 5)
    private String size;

    @ManyToOne
    @JoinColumn(name = "SizeUnitMeasureCode")
    private UnitMeasure sizeUnitMeasure;

    @ManyToOne
    @JoinColumn(name = "WeightUnitMeasureCode")
    private UnitMeasure weightUnitMeasure;

    @Column(name = "Weight", precision = 8, scale = 2)
    private BigDecimal weight;

    @Column(name = "DaysToManufacture", nullable = false)
    private Integer daysToManufacture;

    @Column(name = "ProductLine", length = 2)
    private String productLine;

    @Column(name = "Class", length = 2)
    private String clazz;

    @Column(name = "Style", length = 2)
    private String style;

    @ManyToOne
    @JoinColumn(name = "ProductSubcategoryID")
    private ProductSubcategory productSubcategory;

    @ManyToOne
    @JoinColumn(name = "ProductModelID")
    private ProductModel productModel;

    @Column(name = "SellStartDate", nullable = false)
    private LocalDateTime sellStartDate;

    @Column(name = "SellEndDate")
    private LocalDateTime sellEndDate;

    @Column(name = "DiscontinuedDate")
    private LocalDateTime discontinuedDate;

    @Column(name = "rowguid", nullable = false, unique = true)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}
