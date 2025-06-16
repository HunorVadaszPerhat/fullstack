package com.example.backend.repository.humanresources.employeepayhistory;

import com.example.backend.domain.model.humanresources.employeepayhistory.EmployeePayHistory;
import com.example.backend.domain.model.humanresources.employeepayhistory.EmployeePayHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePayHistoryRepository extends JpaRepository<EmployeePayHistory, EmployeePayHistoryId> {
}
