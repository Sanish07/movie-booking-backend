package com.sanish.booking_service.mappers;

import com.sanish.booking_service.dtos.Booking.BookingAddedEvent;
import com.sanish.booking_service.dtos.Booking.BookingCancelledEvent;
import com.sanish.booking_service.dtos.Booking.BookingErrorEvent;
import com.sanish.booking_service.dtos.Booking.BookingSuccessfulEvent;
import com.sanish.booking_service.dtos.Ticket.TicketDto;
import com.sanish.booking_service.entities.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingEventMapper {
    public static BookingAddedEvent buildBookingAddedEvent(Booking booking) {
        return new BookingAddedEvent(
                UUID.randomUUID().toString(),
                booking.getBookingNumber(),
                getBookingItems(booking),
                booking.getCustomerName(),
                booking.getCustomerEmail(),
                booking.getCustomerPhone(),
                LocalDateTime.now()
        );
    }

    public static BookingSuccessfulEvent buildBookingSuccessfulEvent(Booking booking){
        return new BookingSuccessfulEvent(
                UUID.randomUUID().toString(),
                booking.getBookingNumber(),
                getBookingItems(booking),
                booking.getCustomerName(),
                booking.getCustomerEmail(),
                booking.getCustomerPhone(),
                LocalDateTime.now()
        );
    }

    public static BookingCancelledEvent buildBookingCancelledEvent(Booking booking, String cancellationReason){
        return new BookingCancelledEvent(
                UUID.randomUUID().toString(),
                booking.getBookingNumber(),
                getBookingItems(booking),
                booking.getCustomerName(),
                booking.getCustomerEmail(),
                booking.getCustomerPhone(),
                LocalDateTime.now(),
                cancellationReason
        );
    }

    public static BookingErrorEvent buildBookingErrorEvent(Booking booking, String errorCause){
        return new BookingErrorEvent(
                UUID.randomUUID().toString(),
                booking.getBookingNumber(),
                getBookingItems(booking),
                booking.getCustomerName(),
                booking.getCustomerEmail(),
                booking.getCustomerPhone(),
                LocalDateTime.now(),
                errorCause
        );
    }

    private static List<TicketDto> getBookingItems(Booking savedBooking) {
        return savedBooking.getTickets().stream()
                .map(ticket -> new TicketDto(
                        ticket.getTicketCode(),
                        ticket.getSeatNumber(),
                        ticket.getPrice().doubleValue()))
                .collect(Collectors.toList());
    }
}
