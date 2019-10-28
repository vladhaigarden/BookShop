package com.epam.bookshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class ApplicationException extends RuntimeException {

    @Getter
    protected HttpStatus responseCode;

    public ApplicationException() {

    }

    public ApplicationException(String message) {
        super(message);
    }
}