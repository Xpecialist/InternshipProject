package com.example.SpringPostgress.Enum;

import lombok.Getter;

import java.util.Objects;


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

    public static int checkSeasonString(String val) {
        if (Objects.equals(val, WINTER.season) | Objects.equals(val, AUTUMN.season) | Objects.equals(val, SPRING.season) | Objects.equals(val, SUMMER.season)){
            return 1;
        }
        else{
            return 0;
        }
    }
}
