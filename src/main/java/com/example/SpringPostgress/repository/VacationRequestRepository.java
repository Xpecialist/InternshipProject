package com.example.SpringPostgress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SpringPostgress.model.VacationRequest;
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {
}
