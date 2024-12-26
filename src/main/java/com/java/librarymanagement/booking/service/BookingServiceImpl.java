package com.java.librarymanagement.booking.service;

import com.java.librarymanagement.booking.mapper.BookingMapper;
import com.java.librarymanagement.booking.model.Booking;
import com.java.librarymanagement.booking.model.BookingDTO;
import com.java.librarymanagement.booking.repository.BookingRepository;
import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<BookingDTO> findAll() {

        List<Booking> bookings= this.bookingRepository.findAll();
        return BookingMapper.toDTO(bookings);
    }

    @Override
    public BookingDTO save(Booking entity) {
        User authenticatedUser = userService.fetchSelfInfo();
        entity.setUser(authenticatedUser);
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
