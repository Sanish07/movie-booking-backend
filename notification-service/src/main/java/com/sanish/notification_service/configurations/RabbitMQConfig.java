package com.sanish.notification_service.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanish.notification_service.ApplicationProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//RabbitMQ client will be available at - localhost:15672 (Defined in infra.yml, ports mapping for booking-rabbitmq service)
@Configuration
public class RabbitMQConfig {
    private final ApplicationProperties applicationProperties;

    @Autowired
    public RabbitMQConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    //Defining, Direct exchange as type of routing mediator for RabbitMQ message queues
    @Bean
    DirectExchange exchange(){
        return new DirectExchange(applicationProperties.getBookingsEventsExchange());
    }

    //Creating New Booking Queue
    @Bean
    Queue newBookingsQueue() {
        return QueueBuilder.durable(applicationProperties.getNewBookingsQueue()).build();
    }

    // Binding newBookingsQueue with exchange
    // .with() accepts a String parameter which is essentially the routing-key for a queue binding
    @Bean
    Binding newBookingsQueueBinding() {
        return BindingBuilder.bind(newBookingsQueue()).to(exchange()).with(applicationProperties.getNewBookingsQueue());
    }

    /* ----------------  Similarly for rest of the queues   ---------------- */

    @Bean
    Queue successfulBookingsQueue() {
        return QueueBuilder.durable(applicationProperties.getSuccessfulBookingsQueue()).build();
    }

    @Bean
    Binding successfulBookingsQueueBinding() {
        return BindingBuilder.bind(successfulBookingsQueue()).to(exchange()).with(applicationProperties.getSuccessfulBookingsQueue());
    }

    @Bean
    Queue cancelledBookingsQueue() {
        return QueueBuilder.durable(applicationProperties.getCancelledBookingsQueue()).build();
    }

    @Bean
    Binding cancelledBookingsQueueBinding() {
        return BindingBuilder.bind(cancelledBookingsQueue()).to(exchange()).with(applicationProperties.getCancelledBookingsQueue());
    }

    @Bean
    Queue errorBookingsQueue() {
        return QueueBuilder.durable(applicationProperties.getErrorBookingsQueue()).build();
    }

    @Bean
    Binding errorBookingsQueueBinding() {
        return BindingBuilder.bind(errorBookingsQueue()).to(exchange()).with(applicationProperties.getErrorBookingsQueue());
    }

    //Setting RabbitTemplate for sending messages
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter(objectMapper));
        return rabbitTemplate;
    }

    //Bean for converting messages to JSON formatted structure
    @Bean
    public Jackson2JsonMessageConverter jacksonConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }

}
