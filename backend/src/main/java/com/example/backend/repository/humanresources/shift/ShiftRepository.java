package com.example.backend.repository.humanresources.shift;

import com.example.backend.domain.model.humanresources.shift.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Byte> {
}
