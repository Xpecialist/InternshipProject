package com.example.SpringPostgress.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;
@Entity
@Table(name = "bonus")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bonus")
    @SequenceGenerator(
            name = "seq_bonus",
            sequenceName = "seq_bonus",
            allocationSize = 50,
            initialValue = 1
    )
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties({"firstName","lastName","emailId","startDate","endDate","salary","employmentType","companyId","vacationDays"})
    private Employee employee;

    @ManyToOne
    @JoinColumn(name ="company_id") //put @JoinColumn
    @JsonIgnoreProperties({"name","address","phone"})
    private Company company;

    @Column(name = "amount")
    private float amount;
}
