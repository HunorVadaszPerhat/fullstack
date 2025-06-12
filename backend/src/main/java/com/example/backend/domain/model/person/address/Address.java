package com.example.backend.domain.model.person.address;

import com.example.backend.domain.model.person.stateprovince.StateProvince;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Address", schema = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AddressID", nullable = false)
    private Integer addressId;

    @Column(name = "AddressLine1", nullable = false, length = 60)
    private String addressLine1;

    @Column(name = "AddressLine2", length = 60)
    private String addressLine2;

    @Column(name = "City", nullable = false, length = 30)
    private String city;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "StateProvinceID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_Address_StateProvince_StateProvinceID"))
    private StateProvince stateProvince;

    @Column(name = "PostalCode", nullable = false, length = 15)
    private String postalCode;

    // Placeholder: SQL Server 'geography' requires custom handling
    @Column(name = "SpatialLocation")
    private Object spatialLocation;

    @Column(name = "rowguid", nullable = false)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}