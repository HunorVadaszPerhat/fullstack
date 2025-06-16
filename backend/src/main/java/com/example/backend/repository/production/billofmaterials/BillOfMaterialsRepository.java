package com.example.backend.repository.production.billofmaterials;

import com.example.backend.domain.model.production.billofmaterials.BillOfMaterials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillOfMaterialsRepository extends JpaRepository<BillOfMaterials, Integer> {
}

