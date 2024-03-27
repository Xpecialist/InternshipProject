package com.example.SpringPostgress.DTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PendingRequest extends VacationRequestDTO {

    private long employeeId;
    private String statusFrontend;
    @JsonIgnore
    private int holidays;

}
