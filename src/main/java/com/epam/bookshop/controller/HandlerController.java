package com.epam.bookshop.controller;

import com.epam.bookshop.exception.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handleException(ApplicationException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getResponseCode());
    }
}