package com.example.SpringPostgress.DTO;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductDTO {

    private long id;

    private String name;

    private String description;

    private String barcode;
}
