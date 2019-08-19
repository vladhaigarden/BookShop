package com.epam.bookshop.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException {

    public BadRequestException(String message) {
        super(message);
        this.responseCode = HttpStatus.BAD_REQUEST;
    }
}