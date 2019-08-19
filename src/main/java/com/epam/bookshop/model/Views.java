package com.epam.bookshop.model;

public final class Views {
    public interface Id {
    }

    public interface IdName extends Id {

    }

    public interface IdGenre extends IdName {

    }

    public interface IdPrice extends IdGenre {
    }

    public interface FullProduct extends IdPrice {
    }
}