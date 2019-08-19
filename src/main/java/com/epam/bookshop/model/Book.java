package com.epam.bookshop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Simple JavaBean domain object that represents Book.
 *
 * @author Vladyslav Tereshkevych
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    private Long id;

    @JsonView(Views.Public.class)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('ADVENTURE','DETECTIVE','DRAMA','FANTASY','HORROR','OTHER')")
    @JsonView(Views.Public.class)
    private Genre genre;

    @JsonView(Views.Public.class)
    private BigDecimal price;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy--MM-dd HH:mm:ss")
    @JsonView(Views.Hidden.class)
    private LocalDateTime date;
}