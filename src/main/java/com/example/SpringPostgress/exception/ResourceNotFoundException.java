package com.example.SpringPostgress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 2L;
    public ResourceNotFoundException(String message){
        super(message);
    }
}
