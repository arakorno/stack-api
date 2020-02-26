package com.github.arakorno.stackapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class StackexchangeApiException extends RuntimeException {
    public StackexchangeApiException(String message) {
        super(message);
    }
}