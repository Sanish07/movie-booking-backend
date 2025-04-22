package com.sanish.booking_service.services;

import com.sanish.booking_service.dtos.Booking.BookingRequest;
import com.sanish.booking_service.dtos.Booking.BookingResponse;
import com.sanish.booking_service.entities.Booking;
import com.sanish.booking_service.mappers.BookingMapper;
import com.sanish.booking_service.repositories.BookingRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class BookingServiceImpl implements BookingService{

    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    private final BookingRepository bookingRepository;
    private final MovieServiceValidator movieValidator;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, MovieServiceValidator movieValidator) {
        this.bookingRepository = bookingRepository;
        this.movieValidator = movieValidator;
    }

    @Override
    public BookingResponse addNewBooking(String username, BookingRequest bookingRequest) {
        movieValidator.validate(bookingRequest);
        Booking newBooking = BookingMapper.mapToBooking(bookingRequest);
        newBooking.setUsername(username);
        newBooking.setCreatedAt(LocalDateTime.now());
        newBooking.setUpdatedAt(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(newBooking);
        log.info("Created new booking with bookingNumber : " + savedBooking.getBookingNumber());

        return new BookingResponse(savedBooking.getBookingNumber());
    }
}
