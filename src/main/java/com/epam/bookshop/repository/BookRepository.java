package com.epam.bookshop.repository;

import com.epam.bookshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Book} class.
 *
 * @author Vladyslav Tereshkevych
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}