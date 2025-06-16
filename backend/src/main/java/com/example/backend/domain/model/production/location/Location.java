package com.example.backend.domain.model.production.location;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Location", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LocationID")
    private Short locationId;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @Column(name = "CostRate", nullable = false)
    private BigDecimal costRate;

    @Column(name = "Availability", nullable = false, precision = 8, scale = 2)
    private BigDecimal availability;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

