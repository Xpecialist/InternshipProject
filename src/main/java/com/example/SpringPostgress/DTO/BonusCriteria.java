package com.example.SpringPostgress.DTO;

import com.example.SpringPostgress.service.BonusService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ModelAttribute;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class BonusCriteria {

    private long companyId;
    private float salary;
    private String season;

}
