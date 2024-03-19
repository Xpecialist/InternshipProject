package com.example.SpringPostgress.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CompanyDTO {

    private long id;

    private String name;

    private String address;

    private String phone;

}
