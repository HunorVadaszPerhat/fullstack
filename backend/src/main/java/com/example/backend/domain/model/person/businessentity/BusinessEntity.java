package com.example.backend.domain.model.person.businessentity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "BusinessEntity", schema = "Person")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BusinessEntityID", nullable = false)
    private Integer businessEntityID;

    @Column(name = "rowguid", nullable = false, unique = true)
    private UUID rowguid;
    
    @Column(name = "ModifiedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
}
