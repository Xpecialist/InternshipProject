package com.example.SpringPostgress.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeTemp {
    private long id;
    private String fullName;
    @Override
    public String toString(){
        return this.fullName;
    }
}
