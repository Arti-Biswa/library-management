package com.java.librarymanagement.booking.repository;

import com.java.librarymanagement.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByDueDateBefore(LocalDate currentDate);
}
