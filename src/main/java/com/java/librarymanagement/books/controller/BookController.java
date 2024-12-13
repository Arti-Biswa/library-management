package com.java.librarymanagement.books.controller;

import com.java.librarymanagement.books.mapper.BookMapper;
import com.java.librarymanagement.books.model.Book;
import com.java.librarymanagement.books.model.BookDTO;
import com.java.librarymanagement.books.service.BookService;
import com.java.librarymanagement.utils.RestHelper;
import com.java.librarymanagement.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Adding up the new books by admin.
     *
     * @param bookDTO The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> save(@Validated @RequestBody BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);
        BookDTO savedBookDTO = bookService.save(book);
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("book", savedBookDTO);
        return RestHelper.responseSuccess(responseMap);
    }
}

