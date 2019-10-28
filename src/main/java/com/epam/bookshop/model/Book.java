package com.epam.bookshop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Simple JavaBean domain object that represents Book.
 *
 * @author Vladyslav Tereshkevych
 */

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book {

    @ApiModelProperty(notes = "name of the book")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    @NonNull
    private Long id;

    @NotBlank(message = "Name cannot be empty.")
    @JsonView(Views.Public.class)
    @NonNull
    private String name;

    @Enumerated(EnumType.STRING)
    @JsonView(Views.Public.class)
    @NonNull
    private Genre genre;

    @DecimalMin(value = "0", message = "Price cannot be less than 0.")
    @NotNull(message = "Price cannot be empty.")
    @JsonView(Views.Public.class)
    @NonNull
    private BigDecimal price;

    @Column(name = "creation_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy--MM-dd HH:mm:ss")
    @JsonView(Views.Hidden.class)
    private LocalDateTime date;
}