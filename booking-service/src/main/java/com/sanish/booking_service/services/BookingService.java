package com.sanish.booking_service.services;

import com.sanish.booking_service.dtos.Booking.BookingRequest;
import com.sanish.booking_service.dtos.Booking.BookingResponse;

public interface BookingService {
    BookingResponse addNewBooking(String username, BookingRequest bookingRequest);
    void processNewBookings();
}
