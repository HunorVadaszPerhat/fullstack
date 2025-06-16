package com.example.backend.repository.person.contacttype;

import com.example.backend.domain.model.person.contacttype.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {
}
