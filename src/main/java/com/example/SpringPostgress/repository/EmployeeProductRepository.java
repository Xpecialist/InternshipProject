package com.example.SpringPostgress.repository;

import com.example.SpringPostgress.model.EmployeeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeProductRepository extends JpaRepository<EmployeeProduct, Long> {
    EmployeeProduct findByEmployeeId(Long employeeId);
}
