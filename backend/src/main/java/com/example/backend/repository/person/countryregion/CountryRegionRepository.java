package com.example.backend.repository.person.countryregion;

import com.example.backend.domain.model.person.countryregion.CountryRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRegionRepository extends JpaRepository<CountryRegion, String> {
}
