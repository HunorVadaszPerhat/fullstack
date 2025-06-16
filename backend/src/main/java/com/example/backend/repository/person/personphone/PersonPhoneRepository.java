package com.example.backend.repository.person.personphone;

import com.example.backend.domain.model.person.personphone.PersonPhone;
import com.example.backend.domain.model.person.personphone.PersonPhoneId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonPhoneRepository extends JpaRepository<PersonPhone, PersonPhoneId> {
}

