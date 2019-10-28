package com.epam.bookshop;

import com.epam.bookshop.controller.BookController;
import com.epam.bookshop.exception.BadRequestException;
import com.epam.bookshop.model.Book;
import com.epam.bookshop.model.Genre;
import com.epam.bookshop.repository.BookRepository;
import com.epam.bookshop.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerJUnitTest {

    @Autowired
    private BookController controller;

    @Autowired
    private BookService service;

    @MockBean
    private BookRepository repository;

    @MockBean
    private Page<Book> bookPage;

    private List<Book> books = getBooks();

    @Test
    public void shouldReturnExpectedSizeBooksWhenDefaultPageable() {
        Pageable pageable = PageRequest.of(0, 20);
        when(repository.findAll(pageable)).thenReturn(bookPage);
        when(bookPage.getContent()).thenReturn(books);
        assertEquals(5, service.getAllBooks(pageable).size());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    public void shouldReturnExpectedBook() {
        long id = 1L;
        Book book = books.get(0);
        Optional<Book> optionalBook = Optional.of(book);
        when(repository.findById(id)).thenReturn(optionalBook);
        assertEquals(book, service.getBook(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void shouldReturnCreatedBook() {
        Book book = books.get(0);
        when(repository.save(book)).thenReturn(book);
        assertEquals(book, service.save(book));
        verify(repository, times(1)).save(book);
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowsExceptionWhenTrySaveNull() {
        service.save(null);
        verify(repository, times(0)).save(null);
    }

    @Test
    public void shouldReturnUpdatedBook() {
        Book book = books.get(1);
        Optional<Book> optionalBook = Optional.of(book);
        book.setId(1L);
        book.setName("newName");
        when(repository.findById(1L)).thenReturn(optionalBook);
        when(repository.save(book)).thenReturn(book);
        assertEquals(book, service.update(book));
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(book);
    }

    @Test
    public void shouldSuccessfulDeleteBook() {
        Book book = books.get(0);
        service.delete(book);
        verify(repository, times(1)).delete(book);
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowsExceptionWhenTryDeleteNull() {
        service.delete(null);
        verify(repository, times(0)).delete(null);
    }

    private List<Book> getBooks() {
        long count = 0;
        return Arrays.asList(
                new Book(++count, "Harry Potter", Genre.ADVENTURE, new BigDecimal(25.7)),
                new Book(++count, "Sherlock Holmes", Genre.DETECTIVE, new BigDecimal(21.9)),
                new Book(++count, "The Kite Runner", Genre.DRAMA, new BigDecimal(34.5)),
                new Book(++count, "The Lord of the Rings", Genre.FANTASY, new BigDecimal(51.2)),
                new Book(++count, "It", Genre.HORROR, new BigDecimal(46.6))
        );
    }
}