package com.sanish.booking_service.services;

import com.sanish.booking_service.dtos.Booking.BookingAddedEvent;
import com.sanish.booking_service.dtos.Booking.BookingRequest;
import com.sanish.booking_service.dtos.Booking.BookingResponse;
import com.sanish.booking_service.entities.Booking;
import com.sanish.booking_service.entities.BookingEvent;
import com.sanish.booking_service.entities.BookingStatus;
import com.sanish.booking_service.exceptions.ResourceNotFoundException;
import com.sanish.booking_service.mappers.BookingEventMapper;
import com.sanish.booking_service.mappers.BookingMapper;
import com.sanish.booking_service.repositories.BookingRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService{

    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;
    private final MovieServiceValidator movieValidator;
    private final BookingEventService bookingEventService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, MovieServiceValidator movieValidator, BookingEventService bookingEventService) {
        this.bookingRepository = bookingRepository;
        this.movieValidator = movieValidator;
        this.bookingEventService = bookingEventService;
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

        BookingAddedEvent bookingAddedEvent = BookingEventMapper.buildBookingAddedEvent(savedBooking); //Log Event into database
        bookingEventService.save(bookingAddedEvent); //Save new booking event to booking_events table in database

        return new BookingResponse(savedBooking.getBookingNumber());
    }

    @Override
    public void processNewBookings() {
        List<Booking> bookings = bookingRepository.findByStatus(BookingStatus.NEW);
        log.info("Found {} new bookings to be processed",bookings.size());
        for(Booking booking : bookings){
            processSingleBooking(booking);
        }
    }

    private void processSingleBooking(Booking booking) {
        try{
            if(canBeSuccessfullyProcessed(booking)){ //Verifying & Handling successful bookings
                log.info("Booking Number : {} can be successfully processed!", booking.getBookingNumber());

                Booking updateBooking = bookingRepository.findByBookingNumber(booking.getBookingNumber())
                        .orElseThrow(() -> new ResourceNotFoundException("Booking","bookingNumber", booking.getBookingNumber()));
                updateBooking.setStatus(BookingStatus.SUCCESSFUL);

                bookingRepository.save(updateBooking);
                bookingEventService.save(BookingEventMapper.buildBookingSuccessfulEvent(booking));
            } else{ //Handling cancelled(Failed verification) bookings
                log.info("Problem occurred while processing a booking, Booking Number : {} cannot be successfully processed!", booking.getBookingNumber());

                Booking updateBooking = bookingRepository.findByBookingNumber(booking.getBookingNumber())
                        .orElseThrow(() -> new ResourceNotFoundException("Booking","bookingNumber", booking.getBookingNumber()));
                updateBooking.setStatus(BookingStatus.CANCELLED);

                bookingRepository.save(updateBooking);
                bookingEventService.save(BookingEventMapper.buildBookingCancelledEvent(booking,"Problem in verifying booking details(Customer credentials or payment)."));
            }
        } catch (Exception e) { //Handling system/service error bookings
            log.info("Failed to process a booking, Booking Number : {} cannot be processed due to an internal error!", booking.getBookingNumber());

            Booking updateBooking = bookingRepository.findByBookingNumber(booking.getBookingNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Booking","bookingNumber", booking.getBookingNumber()));
            updateBooking.setStatus(BookingStatus.ERROR);

            bookingRepository.save(updateBooking);
            bookingEventService.save(BookingEventMapper.buildBookingErrorEvent(booking, e.getMessage()));
        }
    }

    //As extension to just field validations we can also add payment validation(by creating payment-service as new microservice) whether the customer
    //has paid for the tickets or not and handle the transaction and booking accordingly.
    //Also when booking enters this method, it should go in IN_PROCESS status if payment validation exists(For the time frame where customer is paying money & doing payment process)
    private boolean canBeSuccessfullyProcessed(Booking booking) {
        return (booking.getCustomerName() != null) &&
                (booking.getCustomerEmail() != null) &&
                (booking.getCustomerPhone() != null) &&
                (booking.getComments() == null);
    }
}
