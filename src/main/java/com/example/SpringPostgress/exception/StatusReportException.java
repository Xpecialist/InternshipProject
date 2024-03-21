package com.example.SpringPostgress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class StatusReportException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    public StatusReportException(String message) { super(message); }
}