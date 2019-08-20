package com.epam.bookshop.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Genre {

    ADVENTURE,
    DETECTIVE,
    DRAMA,
    FANTASY,
    HORROR,
    OTHER;

    @JsonCreator
    public static Genre safeValueOf(String value) {
        try {
            return Genre.valueOf(value);
        } catch (IllegalArgumentException e) {
            return OTHER;
        }
    }
}