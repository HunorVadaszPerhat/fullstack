package com.example.backend.repository.person.businessentitycontact;

import com.example.backend.domain.model.person.businessentitycontact.BusinessEntityContact;
import com.example.backend.domain.model.person.businessentitycontact.BusinessEntityContactId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessEntityContactRepository extends JpaRepository<BusinessEntityContact, BusinessEntityContactId> {
}

