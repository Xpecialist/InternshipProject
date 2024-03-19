package com.example.SpringPostgress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SpringPostgress.model.Bonus;
public interface BonusRepository extends JpaRepository<Bonus, Long> {
}
