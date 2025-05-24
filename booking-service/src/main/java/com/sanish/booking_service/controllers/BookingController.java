package com.sanish.booking_service.controllers;

import com.sanish.booking_service.dtos.Booking.BookingDetailsDto;
import com.sanish.booking_service.dtos.Booking.BookingRequest;
import com.sanish.booking_service.dtos.Booking.BookingResponse;
import com.sanish.booking_service.dtos.Booking.BookingStatusDto;
import com.sanish.booking_service.dtos.ResponseDtos.ErrorResponseDto;
import com.sanish.booking_service.security.SecurityService;
import com.sanish.booking_service.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @Operation(
            summary = "Create New Booking Details",
            description = "REST API endpoint to create new booking details for Movie Booking Application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping
    public ResponseEntity<BookingResponse> createNewBooking(@Valid @RequestBody BookingRequest bookingRequest){
        String getUsername = securityService.getLoginUserName();
        log.info("Creating new booking for user : " + getUsername);
        BookingResponse response = bookingService.addNewBooking(getUsername, bookingRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Fetch Booking Status",
            description = "REST API endpoint to Fetch Booking Status Details by Booking Number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/{bookingNumber}")
    public ResponseEntity<BookingStatusDto> getBookingStatusByBookingNumber(@PathVariable String bookingNumber){
        String username = securityService.getLoginUserName();
        log.info("Finding Booking information for bookingNumber : {}", bookingNumber);
        BookingStatusDto response = bookingService.getBookingByNumber(bookingNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch All User Booking Details",
            description = "REST API endpoint to Fetch All User Booking Details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping
    public ResponseEntity<List<BookingDetailsDto>> getAllUserBookings(){
        String username = securityService.getLoginUserName();
        log.info("Getting all user bookings for user : {}",username);
        List<BookingDetailsDto> allBookings = bookingService.getAllUserBookings(username);
        return new ResponseEntity<>(allBookings,HttpStatus.OK);
    }

}
