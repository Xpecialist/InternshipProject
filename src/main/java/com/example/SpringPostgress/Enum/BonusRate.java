package com.example.SpringPostgress.Enum;

import com.example.SpringPostgress.exception.SeasonStringException;
import lombok.Getter;


/**
 * Enum representing different bonus rates for seasons.
 */
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

    /**
     * Checks if the provided season string matches any of the enum values and returns the corresponding BonusRate.
     *
     * @param season The season string to check.
     * @return The BonusRate corresponding to the provided season.
     * @throws SeasonStringException If the provided season string doesn't match any enum value.
     */
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
