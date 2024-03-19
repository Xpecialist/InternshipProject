package com.example.SpringPostgress.Enum;

import lombok.Getter;

import java.util.Optional;

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

    public static Optional<BonusRate> checkSeasonString(String val) {
        try { return Optional.of(BonusRate.valueOf(val)); }
        catch (Exception e) {/* do nothing */}
        return Optional.empty();
    }
}
