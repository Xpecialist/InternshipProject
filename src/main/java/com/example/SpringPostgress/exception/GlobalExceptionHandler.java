package com.example.SpringPostgress.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling exceptions across the application.
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException and returns an appropriate response.
     *
     * @param ex The ResourceNotFoundException instance.
     * @return ResponseEntity with an error message and HTTP status code 404 (NOT_FOUND).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceException(ResourceNotFoundException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("An error occurred", ex.getMessage());
        log.error("An error occurred: ", ex);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles SeasonStringException and returns an appropriate response.
     * Pings when the input season string is not winter, spring, autumn, summer.
     *
     * @param ex The SeasonStringException instance.
     * @return ResponseEntity with an error message and HTTP status code 409 (CONFLICT).
     */
    @ExceptionHandler({SeasonStringException.class})
    public ResponseEntity<Object> handleSeasonException(SeasonStringException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("Input error occurred", ex.getMessage());
        log.error("Input error occurred: ", ex);

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
    /**
     * Handles VacationDaysException and returns an appropriate response.
     * Pings when the employee doesn't have enough vacation days
     *
     * @param ex The VacationDaysException instance.
     * @return ResponseEntity with an error message and HTTP status code 417 (EXPECTATION_FAILED).
     */
    @ExceptionHandler({VacationDaysException.class})
    public ResponseEntity<Object> handleSeasonException(VacationDaysException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("Input error occurred", ex.getMessage());
        log.error("Input error occurred: ", ex);

        return new ResponseEntity<>(body, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler({NullExpressionException.class, NullPointerException.class})
    public ResponseEntity<Object> handleNullException(NullExpressionException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("Input error occurred", ex.getMessage());
        log.error("Input error occurred: ", ex);

        return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
    }



}
