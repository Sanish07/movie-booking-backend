package com.sanish.notification_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@ConfigurationProperties(prefix = "notifications") //Annotate MainApplication with @ConfigurationPropertiesScan to stage this file for bean management
public class ApplicationProperties {
    String supportEmail;
    String bookingsEventsExchange;
    String newBookingsQueue;
    String successfulBookingsQueue;
    String cancelledBookingsQueue;
    String errorBookingsQueue;
}
