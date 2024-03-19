package com.example.SpringPostgress.model;
import com.example.SpringPostgress.Enum.VacationStatus;
import com.example.SpringPostgress.service.EnumConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vacation_request")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VacationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacation_request_seq")
    @SequenceGenerator(
            name = "vacation_request_seq",
            sequenceName = "vacation_request_seq",
            allocationSize = 50,
            initialValue = 1
    )
    private  long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties({"firstName","lastName","emailId","startDate","endDate","salary","employmentType","companyId","vacationDays"})
    private Employee employee;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    //@Enumerated(EnumType.STRING)
    @Convert(converter = EnumConverter.class)
    private VacationStatus status;

    private int days;

}
