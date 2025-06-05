package com.example.backend.domain.model.person.personphone;

import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.model.person.phonenumbertype.PhoneNumberType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * Telephone number and type of a person.
 */
@Entity
@Table(
        name = "PersonPhone",
        schema = "Person",
        indexes = {
                @Index(name = "IX_PersonPhone_PhoneNumber", columnList = "PhoneNumber")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonPhone {

    @EmbeddedId
    private PersonPhoneId id;

    /**
     * Business entity identification number. Foreign key to Person.BusinessEntityID.
     */
    @ManyToOne
    @MapsId("businessEntityId")
    @JoinColumn(name = "BusinessEntityID", nullable = false)
    private Person person;

    /**
     * Kind of phone number. Foreign key to PhoneNumberType.PhoneNumberTypeID.
     */
    @ManyToOne
    @MapsId("phoneNumberTypeId")
    @JoinColumn(name = "PhoneNumberTypeID", nullable = false)
    private PhoneNumberType phoneNumberType;

    /**
     * Date and time the record was last updated.
     * Automatically set when the entity is first created.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ModifiedDate", nullable = false, columnDefinition = "datetime default getdate()")
    private Date modifiedDate;
}

