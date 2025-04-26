package com.sanish.booking_service.mappers;

import com.sanish.booking_service.dtos.Booking.BookingAddedEvent;
import com.sanish.booking_service.dtos.Ticket.TicketDto;
import com.sanish.booking_service.entities.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingEventMapper {
    public static BookingAddedEvent buildBookingAddedEvent(Booking savedBooking) {
        return new BookingAddedEvent(
                UUID.randomUUID().toString(),
                savedBooking.getBookingNumber(),
                getBookingItems(savedBooking),
                savedBooking.getCustomerName(),
                savedBooking.getCustomerEmail(),
                savedBooking.getCustomerPhone(),
                LocalDateTime.now()
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
