package com.example.SpringPostgress.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_employee")
    @SequenceGenerator(
            name = "seq_employee",
            sequenceName = "seq_employee",
            allocationSize = 50,
            initialValue = 1
    )
    private long id;

    @Column(name = "name")
    private String firstName;

    @Column(name = "surname")
    private String lastName;

    @Column(name = "email")
    private String emailId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "vacation_days")
    private int vacationDays;

    @Column(name = "salary")
    private float salary;

    @Column(name = "employment_type")
    private String employmentType;

    @ManyToOne
    @JoinColumn(name ="company_id")
    @JsonIgnoreProperties({"name","address","phone"})
    private Company company;


}
