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
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('ADVENTURE','DETECTIVE','DRAMA','FANTASY','HORROR')")
    @JsonView(Views.IdGenre.class)
    private Genre genre;

    @JsonView(Views.IdPrice.class)
    private BigDecimal price;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy--MM-dd HH:mm:ss")
    @JsonView(Views.FullProduct.class)
    private LocalDateTime date;
}