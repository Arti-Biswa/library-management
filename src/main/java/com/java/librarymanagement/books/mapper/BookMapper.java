package com.java.librarymanagement.books.mapper;

import com.java.librarymanagement.books.model.Book;
import com.java.librarymanagement.books.model.BookDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    /**
     * Maps the books to book dto.
     *
     * @param book The book entity.
     * @return Returns the book entity.
     */
    public static BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        BeanUtils.copyProperties(book,dto);
        return dto;
    }

    /**
     * Maps the list of books to book dto
     *
     * @param book The list of book entity
     * @return The list of book dto.
     */
    public static List<BookDTO> toDTO(List<Book> book) {
        return book.stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps the optional book to optional book dto.
     *
     * @param book The book entity
     * @return The optional book dto.
     */
    public static Optional<BookDTO> toDTO(Optional<Book> book) {
        return book.map(BookMapper::toDTO);
    }

    /**
     * Maps the book dto  to the book entity.
     *
     * @param dto The book dto.
     * @return The book entity.
     */
    public static Book toEntity(BookDTO dto) {
        if (dto == null) {
            return null;
        }
        Book book = new Book();
        BeanUtils.copyProperties(dto, book); // Ensure this copies the ISBN field
        return book;
    }

}
