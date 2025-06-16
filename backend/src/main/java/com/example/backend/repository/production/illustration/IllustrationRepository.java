package com.example.backend.repository.production.illustration;

import com.example.backend.domain.model.production.illustration.Illustration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IllustrationRepository extends JpaRepository<Illustration, Integer> {
}
