package com.example.backend.repository.person.emailaddress;

import com.example.backend.domain.model.person.emailaddress.EmailAddress;
import com.example.backend.domain.model.person.emailaddress.EmailAddressId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAddressRepository extends JpaRepository<EmailAddress, EmailAddressId> {
}

