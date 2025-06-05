package com.example.backend.domain.model.person.addresstype;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

/**
 * Types of addresses stored in the Address table.
 * For example, Billing, Home, or Shipping.
 */
@Entity
@Table(name = "AddressType",
        indexes = {
                @Index(name = "AK_AddressType_rowguid", columnList = "rowguid", unique = true),
                @Index(name = "AK_AddressType_Name", columnList = "Name", unique = true)
        },
        schema = "Person"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressType {

    /**
     * Primary key for AddressType records.
     * Auto-incremented integer identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AddressTypeID", nullable = false)
    private Integer addressTypeId;

    /**
     * Address type description.
     * For example: Billing, Home, or Shipping.
     */
    @Column(name = "Name", nullable = false, length = 50, unique = true)
    private String name;

    /**
     * ROWGUIDCOL number uniquely identifying the record.
     * Used to support a merge replication sample.
     */
    @Column(name = "rowguid", nullable = false, columnDefinition = "uniqueidentifier default newid()")
    private UUID rowguid = UUID.randomUUID();

    /**
     * Date and time the record was last updated.
     * Automatically set when the entity is first created.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ModifiedDate", nullable = false, columnDefinition = "datetime default getdate()")
    private Date modifiedDate;
}

