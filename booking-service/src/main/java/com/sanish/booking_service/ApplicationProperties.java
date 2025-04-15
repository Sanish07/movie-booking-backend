package com.sanish.booking_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@ConfigurationProperties(prefix = "bookings") //Annotate MainApplication with @ConfigurationPropertiesScan to stage this file for bean management
public class ApplicationProperties {
    String bookingsEventsExchange;
    String newBookingsQueue;
    String successfulBookingsQueue;
    String cancelledBookingsQueue;
    String errorBookingsQueue;
}
