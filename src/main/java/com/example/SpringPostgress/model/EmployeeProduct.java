package com.example.SpringPostgress.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;
@Entity
@Table(name = "employee_product")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_product_seq")
    @SequenceGenerator(
            name = "employee_product_seq",
            sequenceName = "employee_product_seq",
            allocationSize = 50,
            initialValue = 1
    )
    private  long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties({"firstName","lastName","emailId","startDate","endDate","salary","employmentType","companyId","vacationDays"})
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"id"})
    private Product product;
}
