package com.example.backend.domain.model.person.password;

import com.example.backend.domain.model.person.person.Person;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * One way hashed authentication information.
 */
@Entity
@Table(name = "Password", schema = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Password {

    @Id
    @Column(name = "BusinessEntityID", nullable = false)
    private Integer businessEntityId;

    @Column(name = "PasswordHash", nullable = false, length = 128)
    private String passwordHash;

    @Column(name = "PasswordSalt", nullable = false, length = 10)
    private String passwordSalt;

    @Column(name = "rowguid", nullable = false)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "BusinessEntityID", referencedColumnName = "BusinessEntityID")
    private Person person;
}
