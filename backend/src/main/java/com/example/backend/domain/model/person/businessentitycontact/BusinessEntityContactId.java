package com.example.backend.domain.model.person.businessentitycontact;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BusinessEntityContactId implements Serializable {

    @Column(name = "BusinessEntityID")
    private Integer businessEntityId;

    @Column(name = "PersonID")
    private Integer personId;

    @Column(name = "ContactTypeID")
    private Integer contactTypeId;
}
