package com.example.SpringPostgress.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SpringPostgress.model.Company;


public interface CompanyRepository extends JpaRepository<Company, Long> {

}
