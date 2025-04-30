package com.sanish.booking_service.dtos.Booking;

import com.sanish.booking_service.entities.BookingStatus;

public record BookingStatusDto(
        String bookingNumber,
        String customerEmail,
        BookingStatus status) {
}
