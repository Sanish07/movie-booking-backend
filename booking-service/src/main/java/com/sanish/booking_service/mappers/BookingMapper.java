package com.sanish.booking_service.mappers;

import com.sanish.booking_service.dtos.Booking.BookingRequest;
import com.sanish.booking_service.dtos.Ticket.TicketDto;
import com.sanish.booking_service.entities.Booking;
import com.sanish.booking_service.entities.BookingStatus;
import com.sanish.booking_service.entities.Ticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingMapper {
    public static Booking mapToBooking(BookingRequest request){
        List<Ticket> ticketList = new ArrayList<>();

        Booking newBooking = Booking.builder()
                .bookingNumber(UUID.randomUUID().toString())
                .status(BookingStatus.NEW)
                .customerName(request.customerName())
                .customerEmail(request.customerEmail())
                .customerPhone(request.customerPhone())
                .bookingTime(request.bookingTime())
                .build();

        Double totalPrice = 0.0;

        for(TicketDto ticketDto : request.tickets()){
            Ticket ticket = new Ticket();
            ticket.setTicketCode(ticketDto.ticketCode());
            ticket.setSeatNumber(ticketDto.seatNumber());
            ticket.setPrice(BigDecimal.valueOf(ticketDto.price()));
            totalPrice += ticketDto.price().doubleValue();
            ticket.setBooking(newBooking);
            ticketList.add(ticket);
        }

        newBooking.setTickets(ticketList);
        newBooking.setTotalPrice(BigDecimal.valueOf(totalPrice));
        return newBooking;
    }

}
