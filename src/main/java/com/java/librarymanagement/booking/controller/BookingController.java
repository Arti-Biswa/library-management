package com.java.librarymanagement.booking.controller;

import com.java.librarymanagement.booking.model.Booking;
import com.java.librarymanagement.booking.model.BookingDTO;
import com.java.librarymanagement.booking.service.BookingServiceImpl;
import com.java.librarymanagement.utils.RestHelper;
import com.java.librarymanagement.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingService;
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
        BookingDTO savedBookingDTO  = bookingService.save(booking);
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("bookingInfo", savedBookingDTO);
        return RestHelper.responseSuccess(responseMap);
    }
}