package com.epam.bookshop.service;

import com.epam.bookshop.exception.BadRequestException;
import com.epam.bookshop.exception.NotFoundException;
import com.epam.bookshop.model.Book;
import com.epam.bookshop.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        if (Objects.isNull(book)) {
            throw new BadRequestException("Book cannot be empty for saving.");
        }
        book.setDate(LocalDateTime.now());
        log.info("IN BookServiceImpl save {}", book);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        log.info("IN BookServiceImpl getAllBooks");
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new NotFoundException("There are no books.");
        }
        return books;
    }

    @Override
    public Book getBook(Long id) {
        log.info("IN BookServiceImpl getBook {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No book found with id " + id));
    }

    @Override
    public Book update(Book updatedBook) {
        Book bookFromDb = getBook(updatedBook.getId());
        BeanUtils.copyProperties(updatedBook, bookFromDb, "id");
        return bookRepository.save(bookFromDb);
    }

    @Override
    public void delete(Book book) {
        if (Objects.isNull(book)) {
            throw new BadRequestException("Book cannot be empty for removing.");
        }
        log.info("IN BookServiceImpl delete {}", book);
        bookRepository.delete(book);
    }
}