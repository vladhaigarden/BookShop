package com.epam.bookshop.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
        this.responseCode = HttpStatus.NOT_FOUND;
    }
}