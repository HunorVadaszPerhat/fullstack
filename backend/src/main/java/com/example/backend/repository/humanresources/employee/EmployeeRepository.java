package com.example.backend.repository.humanresources.employee;

import com.example.backend.domain.model.humanresources.department.Department;
import com.example.backend.domain.model.humanresources.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Page<Employee> findAll(Pageable pageable);
}
