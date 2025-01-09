package com.java.librarymanagement.booking.service;

import com.java.librarymanagement.booking.mapper.BookingMapper;
import com.java.librarymanagement.booking.model.Booking;
import com.java.librarymanagement.booking.model.BookingDTO;
import com.java.librarymanagement.booking.repository.BookingRepository;
import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.service.UserServiceImpl;
import com.java.librarymanagement.utils.exception.GlobalExceptionWrapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import static com.java.librarymanagement.utils.constants.UserConstants.DELETED_SUCCESSFULLY_MESSAGE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public List<BookingDTO> findAll() {

        List<Booking> bookings= this.bookingRepository.findAll();
        return BookingMapper.toDTO(bookings);
    }

    @Override
    public BookingDTO save(@NonNull Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);
        return BookingMapper.toDTO(savedBooking);
    }

    @Override
    public BookingDTO fetchById(long id) throws Exception {
        return null;
    }

    @Override
    @Transactional
    public String update(long id, @NonNull BookingDTO bookingDTO) {
        // Check if the authenticated user has admin permissions
        User authenticatedUser = userServiceImpl.fetchSelfInfo();
        if (Arrays.stream(authenticatedUser.getRoles().split(","))
                .noneMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))) {
            throw new GlobalExceptionWrapper.ForbiddenException("Only admins can update the available copies of books.");
        }

        // Fetch the Booking from the repository
        Booking booking = findById(id);

        // Update fields from the DTO, if they are not null
        if (bookingDTO.getBookId() != null) {
            booking.setBookId(bookingDTO.getBookId());
        } else {
            throw new GlobalExceptionWrapper.BadRequestException("Book ID is required for booking update.");
        }

        if (bookingDTO.getUserId() != null) {
            // Fetch the User from the database using the userId from DTO
            User user = userServiceImpl.findById(bookingDTO.getUserId());
            if (user != null) {
                booking.setUser(user);
            } else {
                throw new GlobalExceptionWrapper.NotFoundException("User not found with ID: " + bookingDTO.getUserId());
            }
        } else {
            throw new GlobalExceptionWrapper.BadRequestException("User ID is required for booking update.");
        }

        if (bookingDTO.getBorrowedDate() != null) {
            booking.setBorrowedDate(bookingDTO.getBorrowedDate());
        }

        if (bookingDTO.getDueDate() != null) {
            booking.setDueDate(bookingDTO.getDueDate());
        }

        if (bookingDTO.getOverDue() != null) {
            booking.setOverDue(bookingDTO.getOverDue());
        }

        if (bookingDTO.getFineAmount() != null) {
            booking.setFineAmount(bookingDTO.getFineAmount());
        }

        // Save the updated Booking
        bookingRepository.save(booking);
        return "Booking updated successfully.";
    }
    @Override
    @Transactional
    public String deleteById(long id) {
        User authenticatedUser =userServiceImpl.fetchSelfInfo();

        if (Arrays.stream(authenticatedUser.getRoles().split(","))
                .noneMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))) {
            throw new GlobalExceptionWrapper.ForbiddenException("Only admins can delete bookings.");
        }
        Booking book = findById(id);

        this.bookingRepository.deleteById(book.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, "booking");
    }
    private Booking findById(long id) {
        return this.bookingRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(
                        String.format("The specified %s was not found.", "booking")));
    }

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Transactional
    public void updateFines() {
        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking : bookings) {
            try {
                booking.calculateFine(); // Calculate fine dynamically
                bookingRepository.save(booking); // Persist changes
                logger.info("Fine updated for booking ID: {}", booking.getId());
            } catch (Exception e) {
                logger.error("Error updating fine for booking ID: {}", booking.getId(), e);
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void updateFinesTask() {
        logger.info("Scheduled task triggered - updating fines for all bookings");
        updateFines();
    }
}

