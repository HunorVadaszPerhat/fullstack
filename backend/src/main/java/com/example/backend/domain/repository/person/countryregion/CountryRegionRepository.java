package com.example.backend.domain.repository.person.countryregion;

import com.example.backend.domain.model.person.contacttype.ContactType;
import com.example.backend.domain.model.person.countryregion.CountryRegion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRegionRepository extends JpaRepository<CountryRegion, String> {
    Page<CountryRegion> findAll(Pageable pageable);
}
