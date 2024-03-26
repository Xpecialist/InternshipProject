package com.example.SpringPostgress.Enum;

import com.example.SpringPostgress.exception.SeasonStringException;
import lombok.Getter;



@Getter
public enum BonusRate {
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
    public static BonusRate checkBonusSeason(String season){

        BonusRate bonusSeason;
        try {
            bonusSeason = BonusRate.valueOf(season.toUpperCase());
        }
        catch(IllegalArgumentException iae){
            throw new SeasonStringException("This"+ season +"doesn't match with the corresponding seasons.");
        }
        return bonusSeason;
    }

}
