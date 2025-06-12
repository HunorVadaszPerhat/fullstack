package com.example.backend.domain.repository.person.businessentity;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessEntityRepository extends JpaRepository<BusinessEntity, Integer> {
    Page<BusinessEntity> findAll(Pageable pageable);
}