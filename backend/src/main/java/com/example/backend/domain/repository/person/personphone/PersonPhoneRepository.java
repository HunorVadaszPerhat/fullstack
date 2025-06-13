package com.example.backend.domain.repository.person.personphone;

import com.example.backend.domain.model.person.businessentitycontact.BusinessEntityContact;
import com.example.backend.domain.model.person.personphone.PersonPhone;
import com.example.backend.domain.model.person.personphone.PersonPhoneId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonPhoneRepository extends JpaRepository<PersonPhone, PersonPhoneId> {
    Page<PersonPhone> findAll(Pageable pageable);
}

