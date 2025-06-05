package com.example.backend.domain.model.person.personphone;

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
public class PersonPhoneId implements Serializable {

    @Column(name = "BusinessEntityID", nullable = false)
    private Integer businessEntityId;

    @Column(name = "PhoneNumber", nullable = false, length = 25)
    private String phoneNumber;

    @Column(name = "PhoneNumberTypeID", nullable = false)
    private Integer phoneNumberTypeId;
}

