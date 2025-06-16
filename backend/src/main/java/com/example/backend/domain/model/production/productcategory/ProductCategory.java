package com.example.backend.domain.model.production.productcategory;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ProductCategory", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductCategoryID", nullable = false)
    private Integer productCategoryId;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @Column(name = "rowguid", nullable = false, columnDefinition = "uniqueidentifier")
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}
