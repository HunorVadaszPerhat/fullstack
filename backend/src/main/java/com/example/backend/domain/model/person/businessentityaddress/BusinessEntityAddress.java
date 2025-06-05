package com.example.backend.domain.model.person.businessentityaddress;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.addresstype.AddressType;
import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * Cross-reference table mapping customers, vendors, and employees to their addresses.
 */
@Entity
@Table(name = "BusinessEntityAddress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessEntityAddress {

    @EmbeddedId
    private BusinessEntityAddressId id;

    @ManyToOne
    @MapsId("businessEntityId")
    @JoinColumn(name = "BusinessEntityID")
    private BusinessEntity businessEntity;

    @ManyToOne
    @MapsId("addressId")
    @JoinColumn(name = "AddressID")
    private Address address;

    @ManyToOne
    @MapsId("addressTypeId")
    @JoinColumn(name = "AddressTypeID")
    private AddressType addressType;

    /**
     * ROWGUIDCOL number uniquely identifying the record. Used to support a merge replication sample.
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

