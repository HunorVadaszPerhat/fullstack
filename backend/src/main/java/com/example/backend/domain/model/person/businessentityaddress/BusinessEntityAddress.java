package com.example.backend.domain.model.person.businessentityaddress;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.addresstype.AddressType;
import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Cross-reference table mapping customers, vendors, and employees to their addresses.
 */
@Entity
@Table(name = "BusinessEntityAddress", schema = "Person")
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
    @JoinColumn(name = "BusinessEntityID", nullable = false)
    private BusinessEntity businessEntity;

    @ManyToOne
    @MapsId("addressId")
    @JoinColumn(name = "AddressID", nullable = false)
    private Address address;

    @ManyToOne
    @MapsId("addressTypeId")
    @JoinColumn(name = "AddressTypeID", nullable = false)
    private AddressType addressType;

    @Column(name = "rowguid", nullable = false)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

