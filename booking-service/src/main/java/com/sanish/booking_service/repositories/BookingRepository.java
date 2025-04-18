package com.sanish.booking_service.repositories;

import com.sanish.booking_service.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
