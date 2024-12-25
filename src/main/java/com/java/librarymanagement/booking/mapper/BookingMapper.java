package com.java.librarymanagement.booking.mapper;

import com.java.librarymanagement.booking.model.Booking;
import com.java.librarymanagement.booking.model.BookingDTO;
import com.java.librarymanagement.books.mapper.BookMapper;
import com.java.librarymanagement.books.model.Book;
import com.java.librarymanagement.books.model.BookDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookingMapper {
    private static final BigDecimal FINE_PER_DAY = new BigDecimal(5); // Fine of Nu.5 per day

    /**
     * Maps the booking entity to booking DTO.
     *
     * @param booking The booking entity.
     * @return Returns the booking DTO.
     */
    public static BookingDTO toDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();

        // Automatically copy properties using BeanUtils
        BeanUtils.copyProperties(booking, dto); // This will copy fields like name, email, course, etc.

        if(ObjectUtils.isNotEmpty(booking.getUser())){
            dto.setUserId(booking.getUser().getId());
        }

        return dto;
    }

    /**
     * Maps the list of bookings to booking DTOs.
     *
     * @param bookings The list of booking entities.
     * @return The list of booking DTOs.
     */
    public static List<BookingDTO> toDTO(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps the optional book entity to optional book DTO.
     *
     * @param book The book entity.
     * @return The optional book DTO.
     */
    public static Optional<BookDTO> toDTO(Optional<Book> book) {
        return book.map(BookMapper::toDTO);
    }

    /**
     * Calculates the overdue days based on the due date.
     *
     * @param dueDate The due date of the booking.
     * @return The number of overdue days (positive if overdue, zero if not).
     */
    private static int calculateOverdue(LocalDate dueDate) {
        if (dueDate == null) {
            return 0; // No overdue if dueDate is null
        }
        long daysBetween = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        return daysBetween > 0 ? (int) daysBetween : 0; // Return overdue days or 0 if not overdue
    }

    /**
     * Calculates the fine amount based on overdue days.
     *
     * @param overdueDays The number of overdue days.
     * @return The fine amount (Nu.5 per day).
     */
    private static BigDecimal calculateFine(int overdueDays) {
        if (overdueDays <= 0) {
            return BigDecimal.ZERO; // No fine if no overdue
        }
        return FINE_PER_DAY.multiply(new BigDecimal(overdueDays)); // Fine = Nu.5 * overdue days
    }
}
