package com.example.SpringPostgress.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeProductDTO {

    private  long id;

    @JsonIgnoreProperties({"firstName","lastName","emailId","startDate","endDate","salary","employmentType","companyId","vacationDays"})
    private EmployeeDTO employee;


    private ProductDTO product;
}
