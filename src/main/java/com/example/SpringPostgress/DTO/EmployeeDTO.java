package com.example.SpringPostgress.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EmployeeDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String emailId;

    private LocalDate startDate;

    private int vacationDays;

    private float salary;

    private String employmentType;

    @JsonIgnoreProperties({"name","address","phone"})
    private CompanyDTO company;

}