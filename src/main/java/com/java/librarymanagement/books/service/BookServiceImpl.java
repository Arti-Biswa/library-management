package com.java.librarymanagement.books.service;

import com.java.librarymanagement.books.mapper.BookMapper;
import com.java.librarymanagement.books.model.Book;
import com.java.librarymanagement.books.model.BookDTO;
import com.java.librarymanagement.books.repository.BookRepository;
import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.service.UserService;
import com.java.librarymanagement.utils.exception.GlobalExceptionWrapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.java.librarymanagement.utils.constants.UserConstants.*;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<BookDTO> findAll() {
        List<Book> book = this.bookRepository.findAll();
        return BookMapper.toDTO(book);
    }

    @Override
    public BookDTO save(@NonNull Book book) {
        if (bookRepository.existsByISBN(book.getISBN())) {
            throw new GlobalExceptionWrapper.BadRequestException("A book with the same ISBN already exists.");
        }
        Book savedBook = this.bookRepository.save(book);
        return BookMapper.toDTO(savedBook);
    }

    @Override
    public BookDTO fetchById(long id) {
        Book book = findById(id);
        return BookMapper.toDTO(book);
    }

    @Override
    @Transactional
    public String update(long id, @NonNull BookDTO bookDTO) {
        User authenticatedUser = userService.fetchSelfInfo();
        if (Arrays.stream(authenticatedUser.getRoles().split(","))
                .noneMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))) {
            throw new GlobalExceptionWrapper.ForbiddenException("Only admins can update the available copies of books.");
        }

        Book book = findById(id);

        if (bookDTO.getAvailableCopies() != null) {
            book.setAvailableCopies(bookDTO.getAvailableCopies());
        } else {
            throw new GlobalExceptionWrapper.BadRequestException("Available copies field is required for update.");
        }

        bookRepository.save(book);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, "Book's available copies");
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        User authenticatedUser =userService.fetchSelfInfo();

        if (Arrays.stream(authenticatedUser.getRoles().split(","))
                .noneMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))) {
            throw new GlobalExceptionWrapper.ForbiddenException("Only admins can delete books.");
        }
        Book book = findById(id);

        this.bookRepository.deleteById(book.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, "book");
    }

    private Book findById(long id) {
        return this.bookRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(
                        String.format("The specified %s was not found.", "book")));
    }
}
