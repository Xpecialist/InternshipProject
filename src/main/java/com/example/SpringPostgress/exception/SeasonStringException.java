package com.example.SpringPostgress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class SeasonStringException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 3L;
    public SeasonStringException(String message) { super(message); }
}
