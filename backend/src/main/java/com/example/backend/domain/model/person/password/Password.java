package com.example.backend.domain.model.person.password;

import com.example.backend.domain.model.person.person.Person;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

/**
 * One way hashed authentication information.
 */
@Entity
@Table(
        name = "Password",
        schema = "Person"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Password {

    /**
     * Primary key (clustered). Foreign key constraint referencing Person.BusinessEntityID.
     */
    @Id
    @Column(name = "BusinessEntityID", nullable = false)
    private Integer businessEntityId;

    /**
     * Password for the e-mail account.
     */
    @Column(name = "PasswordHash", nullable = false, length = 128)
    private String passwordHash;

    /**
     * Random value concatenated with the password string before the password is hashed.
     */
    @Column(name = "PasswordSalt", nullable = false, length = 10)
    private String passwordSalt;

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

    /**
     * Person owning this password record.
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "BusinessEntityID", referencedColumnName = "BusinessEntityID")
    private Person person;
}
