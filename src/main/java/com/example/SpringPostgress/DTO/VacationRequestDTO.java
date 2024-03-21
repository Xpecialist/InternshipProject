package com.example.SpringPostgress.DTO;
import com.example.SpringPostgress.Enum.VacationStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;

import java.time.LocalDate;




@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VacationRequestDTO {

    private long id; // = vecationId ????

    private EmployeeDTO employee;

    private LocalDate startDate;

    private LocalDate endDate;

    private VacationStatus status;

    private int days;
}
