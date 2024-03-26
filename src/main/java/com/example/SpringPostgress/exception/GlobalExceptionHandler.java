package com.example.SpringPostgress.exception;


import ch.qos.logback.classic.Logger;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceException(ResourceNotFoundException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("An error occurred", ex.getMessage());
        log.error("An error occurred: ", ex);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SeasonStringException.class})
    public ResponseEntity<Object> handleSeasonException(SeasonStringException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("Input error occurred", ex.getMessage());
        log.error("Input error occurred: ", ex);

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({VacationDaysException.class})
    public ResponseEntity<Object> handleSeasonException(VacationDaysException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("Input error occurred", ex.getMessage());
        log.error("Input error occurred: ", ex);

        return new ResponseEntity<>(body, HttpStatus.EXPECTATION_FAILED);
    }


}
