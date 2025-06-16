package com.example.backend.repository.production.culture;

import com.example.backend.domain.model.production.culture.Culture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CultureRepository extends JpaRepository<Culture, String> {
}

