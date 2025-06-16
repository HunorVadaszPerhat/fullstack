package com.example.backend.repository.humanresources.employeedepartmenthistory;

import com.example.backend.domain.model.humanresources.employeedepartmenthistory.EmployeeDepartmentHistory;
import com.example.backend.domain.model.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDepartmentHistoryRepository extends JpaRepository<EmployeeDepartmentHistory, EmployeeDepartmentHistoryId> {
}
