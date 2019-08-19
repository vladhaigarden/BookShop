package com.epam.bookshop.model;

public enum Genre {

    ADVENTURE("adventure"),
    DETECTIVE("detective"),
    DRAMA("drama"),
    FANTASY("fantasy"),
    HORROR("horror");

    private String value;

    Genre(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}