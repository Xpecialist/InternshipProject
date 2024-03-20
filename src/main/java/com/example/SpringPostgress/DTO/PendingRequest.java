package com.example.SpringPostgress.DTO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PendingRequest extends VacationRequestDTO {

    private int holidays;
    private long employeeId;
    private String statusFrontend;

}
