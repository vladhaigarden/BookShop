package com.epam.bookshop.controller;

import com.epam.bookshop.model.Book;
import com.epam.bookshop.model.Views;
import com.epam.bookshop.service.BookService;
import com.epam.bookshop.validator.RequestValidator;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private RequestValidator requestValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book save(@Valid @RequestBody Book book, BindingResult bindingResult) {
        requestValidator.validateRequest(bindingResult);
        return bookService.save(book);
    }

    @GetMapping
    @JsonView(Views.Public.class)
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("{id}")
    @JsonView(Views.Public.class)
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @PutMapping("{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody Book book, BindingResult bindingResult) {
        requestValidator.validateRequest(bindingResult);
        book.setId(id);
        return bookService.update(book);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Book book) {
        bookService.delete(book);
    }
}