package com.java.librarymanagement.books.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private long id;
    private String ISBN;
    private String title;
    private String author;
    private String genre;
    private BigDecimal price;
    private Integer availableCopies;
    private String publisher;
    private Integer publishedYear;
}
