package com.sanish.notification_service.repositories;

import com.sanish.notification_service.entities.BookingEvent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingEventRepository extends JpaRepository<BookingEvent, Long> {
    boolean existsByEventId(String eventId);
}
