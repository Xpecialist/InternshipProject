package com.example.SpringPostgress.Enum;

import com.example.SpringPostgress.exception.NullExpressionException;
import com.example.SpringPostgress.exception.ResourceNotFoundException;

import java.util.Objects;

public enum VacationStatus {
    PENDING,
    APPROVED,
    REJECTED;

    public static void checkStatusValidity(String status){

        if(status == null){
            throw  new NullExpressionException("status is NULL!");
        }
        //ACCEPTED is only a code we get from Frontend != APPROVED
        else if(!(status.equals("ACCEPTED") || status.equals("APPROVED") || status.equals("REJECTED") || status.equals("PENDING"))){
            throw new IllegalArgumentException("Status string: "+status+" doesn't match any acceptable VacationStatus values.");
        }
    }


}
