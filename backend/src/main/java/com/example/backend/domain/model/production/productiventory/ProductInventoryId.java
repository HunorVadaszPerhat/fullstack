package com.example.backend.domain.model.production.productiventory;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventoryId implements Serializable {
    private Integer productId;
    private Integer locationId;
}

