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
    public static Genre safeValueOf(String string) {
        try {
            return Genre.valueOf(string);
        } catch (IllegalArgumentException e) {
            return OTHER;
        }
    }
}