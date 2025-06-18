package com.example.backend.domain.model.production.productmodel;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ProductModel", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductModelID", nullable = false)
    private Integer productModelId;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @Lob
    @Column(name = "CatalogDescription", columnDefinition = "xml")
    private String catalogDescription;

    @Lob
    @Column(name = "Instructions", columnDefinition = "xml")
    private String instructions;

    @Column(name = "rowguid", nullable = false)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

