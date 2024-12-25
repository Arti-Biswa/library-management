package com.java.librarymanagement.books.controller;

import com.java.librarymanagement.books.mapper.BookMapper;
import com.java.librarymanagement.books.model.Book;
import com.java.librarymanagement.books.model.BookDTO;
import com.java.librarymanagement.books.repository.BookRepository;
import com.java.librarymanagement.books.service.BookServiceImpl;
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
    private BookServiceImpl bookServiceImpl;

    @Autowired
    private BookRepository bookRepository;
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
        BookDTO savedBookDTO = bookServiceImpl.save(book);
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("book", savedBookDTO);
        return RestHelper.responseSuccess(responseMap);
    }

    /**
     * Fetches all the user entities in the system.
     *
     * @return The list of user entities.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findAll() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("book", bookServiceImpl.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }
    /**
     * Deletes the user by id.
     *
     * @param id The unique identifier of the entity.
     * @return The message indicating the confirmation on deleted user entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> delete(@PathVariable long id) {
        String message = bookServiceImpl.deleteById(id);
        return RestHelper.responseMessage(message);
    }

    /**
     * Counts the books in the book entity.
     *
     * @return The message indicating the total number of books in book entity.
     */

    @GetMapping("/count")
    public long getTotalBooks() {
        return bookRepository.count();
    }

    /**
     * Updates the existing book entity.
     *
     * @param id The updated book entity.
     * @return The message indicating the confirmation on updated book entity.
     */
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> update(@PathVariable long id, @RequestBody @Validated BookDTO bookDTO) {
        String message = bookServiceImpl.update(id, bookDTO);
        return RestHelper.responseMessage(message);
    }
}

