package com.sanish.booking_service.services;

import com.sanish.booking_service.dtos.Booking.BookingDetailsDto;
import com.sanish.booking_service.dtos.Booking.BookingRequest;
import com.sanish.booking_service.dtos.Booking.BookingResponse;
import com.sanish.booking_service.dtos.Booking.BookingStatusDto;

import java.util.List;

public interface BookingService {
    BookingResponse addNewBooking(String username, BookingRequest bookingRequest);
    void processNewBookings();
    BookingStatusDto getBookingByNumber(String bookingNumber);
    List<BookingDetailsDto> getAllUserBookings(String username);
}
