package com.example.bookingsystem.controllers.flight;

import com.example.bookingsystem.dto.flight.FlightDtoIn;
import com.example.bookingsystem.dto.flight.FlightDtoOut;
import com.example.bookingsystem.services.flight.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
@Tag(name = "Flights", description = "API for managing Flights.")
public class FlightController {
    private final FlightService flightService;

    @Operation(
            summary = "Add new flight to database.",
            description = "Creates a new flight object based on given values."
    )
    @ApiResponse(responseCode = "200", description = "Flight has been added to the database successfully.")
    @PostMapping("flights/add")
    public ResponseEntity<FlightDtoOut> addFlight(@RequestBody FlightDtoIn flightDtoIn) {
        FlightDtoOut flight = flightService.addFlight(flightDtoIn);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieves a list of flights from database based on given arguments.",
            description = "Retrieves flights by flight number, departure airport, arrival airport, departure time " +
                    "and price. Returns all flights (10 per page) if no filters are applied."
    )
    @ApiResponse(responseCode = "200", description = "List of suitable flights has been retrieved successfully.")
    @GetMapping()
    public ResponseEntity<List<FlightDtoOut>> getFlight(
            @RequestParam(value = "flightNumber", required = false) String flightNumber,
            @RequestParam(value = "departureAirport", required = false) String departureAirport,
            @RequestParam(value = "arrivalAirport", required = false) String arrivalAirport,
            @RequestParam(value = "departureTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
            @RequestParam(value = "price", required = false) Double price
    ) {
        List<FlightDtoOut> flightList = flightService.getFlights(flightNumber, departureAirport, arrivalAirport, departureTime, price);
        return new ResponseEntity<>(flightList, HttpStatus.OK);
    }
}
