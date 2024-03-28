package com.example.SpringPostgress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.Serial;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NullExpressionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NullExpressionException(String message) {
        super(message);
    }
}
