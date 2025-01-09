package com.java.librarymanagement.booking.controller;

import com.java.librarymanagement.booking.mapper.BookingMapper;
import com.java.librarymanagement.booking.model.Booking;
import com.java.librarymanagement.booking.model.BookingDTO;
import com.java.librarymanagement.booking.service.BookingServiceImpl;
import com.java.librarymanagement.utils.RestHelper;
import com.java.librarymanagement.utils.RestResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingServiceImpl;

    /**
     * Creates a new booking.
     *
     * This endpoint allows 'ADMIN' users to create a booking by sending a valid `Booking` object in the request body.
     * Upon success, it returns the borrowed booking details.
     *
     * @param bookingDTO The booking information to be created.
     * @return A {@link ResponseEntity} with the saved booking details.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> save(@Validated @RequestBody @NonNull BookingDTO bookingDTO) {
        // Convert the BookingDTO to Booking entity
        Booking booking = BookingMapper.toEntity(bookingDTO);  // Assuming you have a BookingMapper to convert DTO to entity

        // Save the Booking entity
        BookingDTO savedBooking = bookingServiceImpl.save(booking);

        // Convert saved Booking entity back to BookingDTO
        BookingDTO savedBookingDTO = BookingMapper.toDTO(booking);

        // Prepare the response map
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("bookingInfo", savedBookingDTO);
        return RestHelper.responseSuccess(responseMap);
    }


    /**
     * Fetches all the booking entities in the system.
     *
     * @return The list of booking entities.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findAll() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("bookingInfo", bookingServiceImpl.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Updates the existing booking entity.
     *
     * @param id The updated booking entity.
     * @return The message indicating the confirmation on updated booking entity.
     */
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> update(@PathVariable long id, @RequestBody @Validated BookingDTO bookingDTO) {
        String message = bookingServiceImpl.update(id, bookingDTO);
        return RestHelper.responseMessage(message);
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
        String message = bookingServiceImpl.deleteById(id);
        return RestHelper.responseMessage(message);
    }
}
