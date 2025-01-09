package com.java.librarymanagement.booking.mapper;

import com.java.librarymanagement.booking.model.Booking;
import com.java.librarymanagement.booking.model.BookingDTO;
import com.java.librarymanagement.users.model.User;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    private static final BigDecimal FINE_PER_DAY = new BigDecimal(5); // Fine of Nu.5 per day

    /**
     * Maps a list of booking entities to booking DTOs.
     *
     * @param bookings The list of booking entities.
     * @return The list of booking DTOs.
     */
    public static List<BookingDTO> toDTO(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingMapper::toDTO) // Convert each booking to its DTO
                .collect(Collectors.toList()); // Collect the converted DTOs into a list
    }

    /**
     * Maps the booking entity to booking DTO.
     *
     * @param booking The booking entity.
     * @return Returns the booking DTO.
     */
    public static BookingDTO toDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        BeanUtils.copyProperties(booking, dto);

        // Set userId from the user entity if present
        if (ObjectUtils.isNotEmpty(booking.getUser())) {
            dto.setUserId(booking.getUser().getId());  // Assuming userId is an ID in DTO
        }

        return dto;
    }

    /**
     * Maps the booking DTO to booking entity.
     *
     * @param bookingDTO The booking DTO.
     * @return Returns the booking entity.
     */
    public static Booking toEntity(BookingDTO bookingDTO) {
        System.out.println("UserId from DTO: " + bookingDTO.getUserId());  // Debugging line

        Booking booking = new Booking();
        BeanUtils.copyProperties(bookingDTO, booking);

        // Set the userId in the entity if userId is present in the DTO
        if (bookingDTO.getUserId() != null) {
            User user = new User();
            user.setId(bookingDTO.getUserId());  // Set the user ID in the User entity
            booking.setUser(user);  // Set the full User entity in the booking
        }

        return booking;
    }
}
