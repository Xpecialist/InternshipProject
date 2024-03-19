package com.example.SpringPostgress.repository;

import com.example.SpringPostgress.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
