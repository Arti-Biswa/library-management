package com.java.librarymanagement.booking.model;

import com.java.librarymanagement.books.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long bookingId;  // Booking ID

    private User user;  // Associated User entity
    private Book book;  // Associated Book entity

    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private int overDue;
    private BigDecimal fineAmount;

    private static final BigDecimal DAILY_FINE = new BigDecimal("1.00");  // Fine per day overdue

    // Getters and setters for all fields
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    // Getters and setters for user
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    // Getters and setters for book
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getOverDue() {
        return overDue;
    }

    public void setOverDue(int overDue) {
        this.overDue = overDue;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    // Method to calculate overdue days and fine amount
    public void calculateOverdueAndFine(LocalDate currentDate) {
        if (currentDate.isAfter(dueDate)) {
            this.overDue = (int) ChronoUnit.DAYS.between(dueDate, currentDate);
            this.fineAmount = DAILY_FINE.multiply(new BigDecimal(overDue));
        } else {
            this.overDue = 0;
            this.fineAmount = BigDecimal.ZERO;
        }
    }
    // Override toString to reflect the new structure
    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingId=" + bookingId +
                ", user=" + user +
                ", book=" + book +
                ", borrowedDate=" + borrowedDate +
                ", dueDate=" + dueDate +
                ", overDue=" + overDue +
                ", fineAmount=" + fineAmount +
                '}';
    }

}
