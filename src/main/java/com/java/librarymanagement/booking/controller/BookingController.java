package com.java.librarymanagement.booking.controller;

import com.java.librarymanagement.booking.model.Booking;
import com.java.librarymanagement.booking.model.BookingDTO;
import com.java.librarymanagement.booking.service.BookingServiceImpl;
import com.java.librarymanagement.books.service.BookServiceImpl;
import com.java.librarymanagement.utils.RestHelper;
import com.java.librarymanagement.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
     * @param booking The booking information to be created.
     * @return A {@link ResponseEntity} with the saved booking details.
     *
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> save(@Validated @RequestBody Booking booking) {
        BookingDTO savedBookingDTO  = bookingServiceImpl.save(booking);
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
}
