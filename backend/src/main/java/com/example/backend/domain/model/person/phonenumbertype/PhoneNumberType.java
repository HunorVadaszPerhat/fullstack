package com.example.backend.domain.model.person.phonenumbertype;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * Type of phone number of a person.
 */
@Entity
@Table(
        name = "PhoneNumberType",
        schema = "Person"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumberType {

    /**
     * Primary key for telephone number type records.
     * Clustered index created by a primary key constraint.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PhoneNumberTypeID")
    private Integer phoneNumberTypeId;

    /**
     * Name of the telephone number type.
     */
    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    /**
     * Date and time the record was last updated.
     * Automatically set when the entity is first created.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ModifiedDate", nullable = false, columnDefinition = "datetime default getdate()")
    private Date modifiedDate;
}
