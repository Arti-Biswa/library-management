package com.java.librarymanagement.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.dialect.function.TruncFunction.DatetimeTrunc;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    /**
     * Represents a booking for a library book.
     *
     * This entity stores information about a booking, including the book ID, user who made the booking,
     * borrowed date, due date, overdue amount, and fine amount. It also inherits audit fields from.
     *
     * @param id The unique identifier for the booking.
     * @param bookId The ID of the book being booked.
     * @param user The user who made the booking.
     * @param borrowedDate The date the book was borrowed.
     * @param dueDate The date the book is due to be returned.
     * @param overDue The overdue amount if the book is returned late.
     * @param fineAmount The fine amount due for the overdue book.

     */
    private Long bookId;
    private Long userId;
    private LocalDateTime borrowedDate;
    private LocalDateTime  dueDate;
    private Double overDue;
    private Double fineAmount;
    private Long id;
}