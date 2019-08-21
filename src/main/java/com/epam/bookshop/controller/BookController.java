package com.epam.bookshop.controller;

import com.epam.bookshop.exception.BadRequestException;
import com.epam.bookshop.model.Book;
import com.epam.bookshop.model.Views;
import com.epam.bookshop.service.BookService;
import com.epam.bookshop.util.ErrorExtractor;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book save(@Valid @RequestBody Book book, BindingResult bindingResult) {
        validate(bindingResult);
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
        validate(bindingResult);
        book.setId(id);
        return bookService.update(book);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Book book) {
        bookService.delete(book);
    }

    private void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ErrorExtractor.getErrors(bindingResult));
        }
    }
}