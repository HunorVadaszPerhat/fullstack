package com.example.backend.repository.humanresources.department;

import com.example.backend.domain.model.humanresources.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Short> {
}
