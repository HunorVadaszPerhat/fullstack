package com.example.backend.domain.model.person.businessentity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "BusinessEntity",
        uniqueConstraints = @UniqueConstraint(name = "AK_BusinessEntity_rowguid", columnNames = "rowguid"),
        schema = "Person"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessEntity {
    /**
     * Primary key for all customers, vendors, and employees.
     * Clustered index created by a primary key constraint.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BusinessEntityID")
    private Integer businessEntityId;

    /**
     * ROWGUIDCOL number uniquely identifying the record.
     * Used to support a merge replication sample.
     */
    @Column(name = "rowguid", nullable = false, columnDefinition = "uniqueidentifier default newid()")
    private UUID rowguid = UUID.randomUUID();

    /**
     * Date and time the record was last updated.
     */
    @Column(name = "ModifiedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
}
