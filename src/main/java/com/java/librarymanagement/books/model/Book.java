package com.java.librarymanagement.books.model;

import com.java.librarymanagement.utils.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "books")
public class Book extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_SEQUENCE")
    @SequenceGenerator(name = "BOOK_SEQUENCE", sequenceName = "book_seq", allocationSize = 1)
    private long id;

    @Column(unique = true, nullable = false)
    @NotNull(message = "ISBN cannot be null")
    private String ISBN;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column
    private String genre;

    @Column
    private BigDecimal price;

    @Column
    private Integer availableCopies;

    @Column
    private String publisher;

    @Column
    private Integer publishedYear;
}
