package com.sanish.booking_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanish.booking_service.dtos.Booking.BookingAddedEvent;
import com.sanish.booking_service.dtos.Booking.BookingCancelledEvent;
import com.sanish.booking_service.dtos.Booking.BookingErrorEvent;
import com.sanish.booking_service.dtos.Booking.BookingSuccessfulEvent;
import com.sanish.booking_service.entities.BookingEvent;
import com.sanish.booking_service.entities.BookingEventType;
import com.sanish.booking_service.repositories.BookingEventRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BookingEventServiceImpl implements BookingEventService {

    private static final Logger log = LoggerFactory.getLogger(BookingEventServiceImpl.class);
    private final BookingEventRepository bookingEventRepository;
    private final BookingEventPublisher bookingEventPublisher;
    private final ObjectMapper objectMapper;

    @Autowired
    public BookingEventServiceImpl(BookingEventRepository bookingEventRepository, BookingEventPublisher bookingEventPublisher, ObjectMapper objectMapper) {
        this.bookingEventRepository = bookingEventRepository;
        this.bookingEventPublisher = bookingEventPublisher;
        this.objectMapper = objectMapper;
    }

    @Override
    public void save(BookingAddedEvent event) {
        BookingEvent bookingEvent = new BookingEvent();
        bookingEvent.setEventId(event.eventId());
        bookingEvent.setEventType(BookingEventType.BOOKING_ADDED);
        bookingEvent.setBookingNumber(event.bookingNumber());
        bookingEvent.setPayload(convertPayloadToString(event));
        bookingEvent.setCreatedAt(event.createdAt());

        bookingEventRepository.save(bookingEvent);
    }

    @Override
    public void save(BookingSuccessfulEvent event) {
        BookingEvent bookingEvent = new BookingEvent();
        bookingEvent.setEventId(event.eventId());
        bookingEvent.setEventType(BookingEventType.BOOKING_SUCCESSFUL);
        bookingEvent.setBookingNumber(event.bookingNumber());
        bookingEvent.setPayload(convertPayloadToString(event));
        bookingEvent.setCreatedAt(event.createdAt());

        bookingEventRepository.save(bookingEvent);
    }

    @Override
    public void save(BookingCancelledEvent event) {
        BookingEvent bookingEvent = new BookingEvent();
        bookingEvent.setEventId(event.eventId());
        bookingEvent.setEventType(BookingEventType.BOOKING_CANCELLED);
        bookingEvent.setBookingNumber(event.bookingNumber());
        bookingEvent.setPayload(convertPayloadToString(event));
        bookingEvent.setCreatedAt(event.createdAt());

        bookingEventRepository.save(bookingEvent);
    }

    @Override
    public void save(BookingErrorEvent event) {
        BookingEvent bookingEvent = new BookingEvent();
        bookingEvent.setEventId(event.eventId());
        bookingEvent.setEventType(BookingEventType.BOOKING_PROCESSING_FAILED);
        bookingEvent.setBookingNumber(event.bookingNumber());
        bookingEvent.setPayload(convertPayloadToString(event));
        bookingEvent.setCreatedAt(event.createdAt());

        bookingEventRepository.save(bookingEvent);
    }

    @Override
    public void publishBookingEvents() {
        Sort sort = Sort.by("createdAt").ascending();
        List<BookingEvent> eventList = bookingEventRepository.findAll(sort);
        log.info("Found {} Booking Events to be published", eventList.size());
        for(BookingEvent event : eventList){
            this.publishEvent(event);
            bookingEventRepository.delete(event);
        }
    }

    private void publishEvent(BookingEvent event) {
        BookingEventType eventType = event.getEventType();
        switch(eventType){
            case BOOKING_ADDED:
                BookingAddedEvent bookingAddedEvent = convertStringToJsonPayload(event.getPayload(),BookingAddedEvent.class);
                bookingEventPublisher.publish(bookingAddedEvent);
                break;
            case BOOKING_SUCCESSFUL:
                BookingSuccessfulEvent bookingSuccessfulEvent = convertStringToJsonPayload(event.getPayload(),BookingSuccessfulEvent.class);
                bookingEventPublisher.publish(bookingSuccessfulEvent);
                break;
            case BOOKING_CANCELLED:
                BookingCancelledEvent bookingCancelledEvent = convertStringToJsonPayload(event.getPayload(),BookingCancelledEvent.class);
                bookingEventPublisher.publish(bookingCancelledEvent);
                break;
            case BOOKING_PROCESSING_FAILED:
                BookingErrorEvent bookingErrorEvent = convertStringToJsonPayload(event.getPayload(),BookingErrorEvent.class);
                bookingEventPublisher.publish(bookingErrorEvent);
                break;
            default:
                log.warn("Unsupported BookingEventType: {}", eventType);
        }
    }

    private <T>T convertStringToJsonPayload(String payloadString, Class<T> type) {
        try{
          return objectMapper.readValue(payloadString,type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertPayloadToString(Object object) {
        try{
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
