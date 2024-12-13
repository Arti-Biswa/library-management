package com.java.librarymanagement.books.repository;

import com.java.librarymanagement.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    boolean existsByISBN(String isbn);
}
