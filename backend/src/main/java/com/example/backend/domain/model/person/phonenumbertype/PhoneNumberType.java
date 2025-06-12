package com.example.backend.domain.model.person.phonenumbertype;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Type of phone number of a person.
 */
@Entity
@Table(name = "PhoneNumberType", schema = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumberType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PhoneNumberTypeID", nullable = false)
    private Integer phoneNumberTypeId;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}
