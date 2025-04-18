package com.sanish.booking_service.controllers;

import com.sanish.booking_service.dtos.Booking.BookingRequest;
import com.sanish.booking_service.dtos.Booking.BookingResponse;
import com.sanish.booking_service.security.SecurityService;
import com.sanish.booking_service.services.BookingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/bookings", produces = {MediaType.APPLICATION_JSON_VALUE})
public class BookingController {
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final SecurityService securityService;
    private final BookingService bookingService;

    @Autowired
    public BookingController(SecurityService securityService, BookingService bookingService) {
        this.securityService = securityService;
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createNewBooking(@Valid @RequestBody BookingRequest bookingRequest){
        String getUsername = securityService.getLoginUserName();
        log.info("Creating new booking for user : " + getUsername);
        BookingResponse response = bookingService.addNewBooking(getUsername, bookingRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
