package com.example.SpringPostgress.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringPostgress.model.Employee;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    List<Employee> findByCompanyId(long companyId);

    @Query("""
            SELECT e.firstName ,e.lastName ,p.name,p.description ,p.barcode, p.id
            FROM Employee e
            JOIN EmployeeProduct ep\s
            JOIN Product p\s
            WHERE e.company.id = :companyId""")
    List<Object[]> findEmployeeProductsByCompanyId(int companyId);

}