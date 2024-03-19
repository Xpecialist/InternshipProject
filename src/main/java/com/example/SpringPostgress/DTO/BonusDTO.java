package com.example.SpringPostgress.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BonusDTO {

    private long id;

    private EmployeeDTO employee;

    private CompanyDTO company;

    private float amount;
}