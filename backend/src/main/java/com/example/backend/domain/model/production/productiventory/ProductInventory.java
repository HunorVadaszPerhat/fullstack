package com.example.backend.domain.model.production.productiventory;

import com.example.backend.domain.model.production.location.Location;
import com.example.backend.domain.model.production.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ProductInventory", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInventory {

    @EmbeddedId
    private ProductInventoryId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne
    @MapsId("locationId")
    @JoinColumn(name = "LocationID", nullable = false)
    private Location location;

    @Column(name = "Shelf", nullable = false, length = 10)
    private String shelf;

    @Column(name = "Bin", nullable = false)
    private Integer bin;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "rowguid", nullable = false)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}
