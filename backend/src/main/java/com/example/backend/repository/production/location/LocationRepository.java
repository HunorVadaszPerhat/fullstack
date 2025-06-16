package com.example.backend.repository.production.location;

import com.example.backend.domain.model.production.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Short> {
}
