package com.epam.bookshop.service;

import com.epam.bookshop.model.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    List<Book> getAllBooks();

    Book getBook(Long id);

    Book update(Book book);

    void delete(Book book);
}