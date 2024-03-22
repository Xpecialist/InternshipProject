package com.example.SpringPostgress.Enum;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;


@Getter
public enum BonusRate {//resolve by static block
    WINTER("winter", 1.3),
    AUTUMN("autumn", 0.4),
    SPRING("spring", 0.6),
    SUMMER("summer", 0.7);
    private final String season;
    private final Double rate;

    BonusRate(String season, Double rate) {
        this.season = season;
        this.rate = rate;
    }

}
