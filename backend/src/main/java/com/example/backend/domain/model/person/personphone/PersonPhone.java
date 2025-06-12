package com.example.backend.domain.model.person.personphone;

import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.model.person.phonenumbertype.PhoneNumberType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Telephone number and type of a person.
 */
@Entity
@Table(name = "PersonPhone", schema = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonPhone {

    @EmbeddedId
    private PersonPhoneId id;

    @ManyToOne
    @MapsId("businessEntityId")
    @JoinColumn(name = "BusinessEntityID", nullable = false)
    private Person person;

    @ManyToOne
    @MapsId("phoneNumberTypeId")
    @JoinColumn(name = "PhoneNumberTypeID", nullable = false)
    private PhoneNumberType phoneNumberType;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

