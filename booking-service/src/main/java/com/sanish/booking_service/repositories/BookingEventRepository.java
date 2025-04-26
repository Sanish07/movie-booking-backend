package com.sanish.booking_service.repositories;

import com.sanish.booking_service.entities.BookingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingEventRepository extends JpaRepository<BookingEvent, Long>{
}
