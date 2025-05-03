package com.sanish.booking_service.repositories;

import com.sanish.booking_service.entities.Booking;
import com.sanish.booking_service.entities.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByStatus(BookingStatus status);
    Optional<Booking> findByBookingNumber(String bookingNumber);
    List<Booking> findByUsername(String username);
}
