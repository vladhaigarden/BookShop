package com.epam.bookshop.service;

import com.epam.bookshop.model.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Book save(Book book);

    List<Book> getAllBooks(Pageable pageable);

    List<Book> getAllBooks(String search, Pageable pageable);

    Book getBook(Long id);

    Book update(Book book);

    void delete(Book book);
}