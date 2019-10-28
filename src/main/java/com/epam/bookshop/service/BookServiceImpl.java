package com.epam.bookshop.service;

import com.epam.bookshop.exception.BadRequestException;
import com.epam.bookshop.exception.NotFoundException;
import com.epam.bookshop.model.Book;
import com.epam.bookshop.repository.BookRepository;
import com.epam.bookshop.specification.BookSpecificationsBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        log.info("IN BookServiceImpl save {}", book);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks(Pageable pageable) {
        log.info("IN BookServiceImpl getAllBooks");
        List<Book> books = bookRepository.findAll(pageable).getContent();
        if (books.isEmpty()) {
            throw new NotFoundException("There are no books.");
        }
        return books;
    }

    @Override
    public List<Book> getAllBooks(String search, Pageable pageable) {
        BookSpecificationsBuilder builder = new BookSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(.+?)(:|<|>)(.+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Book> specification = builder.build();
        return bookRepository.findAll(specification, pageable).getContent();
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
        BeanUtils.copyProperties(updatedBook, bookFromDb, "id", "date");
        log.info("IN BookServiceImpl update {}", bookFromDb);
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