package com.sanish.booking_service.services;

import com.sanish.booking_service.dtos.Booking.BookingAddedEvent;
import com.sanish.booking_service.dtos.Booking.BookingCancelledEvent;
import com.sanish.booking_service.dtos.Booking.BookingErrorEvent;
import com.sanish.booking_service.dtos.Booking.BookingSuccessfulEvent;

public interface BookingEventService {
    void save(BookingAddedEvent event);
    void save(BookingSuccessfulEvent event);
    void save(BookingCancelledEvent event);
    void save(BookingErrorEvent event);
    void publishBookingEvents();
}
