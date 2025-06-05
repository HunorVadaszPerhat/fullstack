package com.example.backend.domain.model.person.businessentityaddress;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class BusinessEntityAddressId implements Serializable {

    @Column(name = "BusinessEntityID")
    private Integer businessEntityId;

    @Column(name = "AddressID")
    private Integer addressId;

    @Column(name = "AddressTypeID")
    private Integer addressTypeId;
}
