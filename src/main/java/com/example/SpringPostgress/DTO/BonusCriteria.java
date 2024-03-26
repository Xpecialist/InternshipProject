package com.example.SpringPostgress.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import io.swagger.v3.oas.annotations.Parameter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@ParameterObject
public class BonusCriteria {

    @Parameter(name="companyId")
    private long companyId;

    @Parameter(name="salary")
    private float salary;

    @Parameter(name="season")
    private String season;

}
