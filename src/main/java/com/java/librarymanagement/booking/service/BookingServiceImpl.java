package com.java.librarymanagement.booking.service;

import com.java.librarymanagement.booking.mapper.BookingMapper;
import com.java.librarymanagement.booking.model.Booking;
import com.java.librarymanagement.booking.model.BookingDTO;
import com.java.librarymanagement.booking.repository.BookingRepository;
import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private IUserService userService;
    // Method to update overdue days and fine amounts for all bookings
    public void updateOverdueAndFines(LocalDate currentDate) {
        // Fetch all bookings where the due date is before the current date
        List<Booking> bookings = bookingRepository.findByDueDateBefore(currentDate);

        for (Booking booking : bookings) {
            // Calculate overdue and fine for each booking
            booking.calculateOverdueAndFine(currentDate);
            // Save the updated booking with overdue and fine
            bookingRepository.save(booking);
        }
    }
    // Method to get all overdue books
    public List<Booking> getAllOverdueBooks(LocalDate currentDate) {
        return bookingRepository.findByDueDateBefore(currentDate);
    }

    // Method to get all overdue books with overdue days calculated
    public List<Booking> getAllOverdueBooksWithDays(LocalDate currentDate) {
        // Fetch all overdue bookings (dueDate before currentDate)
        List<Booking> overdueBookings = bookingRepository.findByDueDateBefore(currentDate);

        // For each booking, calculate the overdue days
        overdueBookings.forEach(booking -> {
            int overdueDays = calculateOverdueDays(booking.getDueDate(), currentDate);
            booking.setOverDue(overdueDays);  // Set the overdue days
        });

        return overdueBookings;
    }

    // Helper method to calculate overdue days
    private int calculateOverdueDays(LocalDate dueDate, LocalDate currentDate) {
        if (dueDate != null && dueDate.isBefore(currentDate)) {
            return (int) java.time.temporal.ChronoUnit.DAYS.between(dueDate, currentDate);
        }
        return 0;  // No overdue days
    }

    @Override
    public List<BookingDTO> findAll() {
        return List.of();
    }

    @Override
    public BookingDTO save(Booking entity) {
        User authenticatedUser = userService.fetchSelfInfo();
        entity.setUser(authenticatedUser);
        Booking savedBooking = bookingRepository.save(entity);
        Booking savedBooking = bookingRepository.save(entity);
        return BookingMapper.toDTO(savedBooking);
    }

    @Override
    public BookingDTO fetchById(long id) throws Exception {
        return null;
    }

    @Override
    public String update(long id, BookingDTO entity) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        return "";
    }
}
