package com.example.SpringPostgress.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringPostgress.model.Employee;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    List<Employee> findByCompanyId(long companyId);

}