package com.example.backend.domain.repository.person;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessEntityRepository extends JpaRepository<BusinessEntity, Integer> {
}