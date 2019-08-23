package com.epam.bookshop.validator;

import com.epam.bookshop.exception.BadRequestException;
import com.epam.bookshop.util.ErrorExtractor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public final class RequestValidator {

    public void validateRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ErrorExtractor.getErrors(bindingResult));
        }
    }
}