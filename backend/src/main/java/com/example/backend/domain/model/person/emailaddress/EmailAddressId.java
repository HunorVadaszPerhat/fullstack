package com.example.backend.domain.model.person.emailaddress;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Embeddable
public class EmailAddressId implements Serializable {

    @Column(name = "BusinessEntityID", nullable = false)
    private Integer businessEntityId;

    @Column(name = "EmailAddressID", nullable = false)
    private Integer emailAddressId;
}
