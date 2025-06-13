package com.example.backend.domain.repository.person.businessentitycontact;

import com.example.backend.domain.model.person.businessentityaddress.BusinessEntityAddress;
import com.example.backend.domain.model.person.businessentitycontact.BusinessEntityContact;
import com.example.backend.domain.model.person.businessentitycontact.BusinessEntityContactId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessEntityContactRepository extends JpaRepository<BusinessEntityContact, BusinessEntityContactId> {
    Page<BusinessEntityContact> findAll(Pageable pageable);
}

