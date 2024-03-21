package com.example.SpringPostgress.DTO;


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