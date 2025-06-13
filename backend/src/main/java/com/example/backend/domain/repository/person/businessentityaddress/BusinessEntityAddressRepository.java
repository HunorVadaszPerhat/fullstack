package com.example.backend.domain.repository.person.businessentityaddress;

import com.example.backend.domain.model.person.businessentityaddress.BusinessEntityAddress;
import com.example.backend.domain.model.person.businessentityaddress.BusinessEntityAddressId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessEntityAddressRepository extends JpaRepository<BusinessEntityAddress, BusinessEntityAddressId> {
    Page<BusinessEntityAddress> findAll(Pageable pageable);
}

