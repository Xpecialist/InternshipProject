package com.example.SpringPostgress.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class BonusCriteria {

    private long companyId;
    private float salary;
    private String season;

}
