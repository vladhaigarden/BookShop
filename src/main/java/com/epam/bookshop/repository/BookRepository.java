package com.epam.bookshop.repository;

import com.epam.bookshop.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository interface for {@link Book} class.
 *
 * @author Vladyslav Tereshkevych
 */
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Page<Book> findAll(Pageable pageable);

    List<Book> findByNameNotLikeAndPriceGreaterThan(String name, BigDecimal price);
}