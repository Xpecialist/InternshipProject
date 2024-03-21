package com.example.SpringPostgress.exception;


import ch.qos.logback.classic.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger log;
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceException(ResourceNotFoundException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("An error occurred", ex.getMessage());
        log.error("An error occurred: ", ex);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SeasonStringException.class, VacationDaysException.class})
    public ResponseEntity<Object> handleSeasonException(SeasonStringException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("Input error occurred", ex.getMessage());
        log.error("Input error occurred: ", ex);

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


}
