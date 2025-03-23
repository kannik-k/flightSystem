package com.example.bookingsystem.controllers.flight;

import com.example.bookingsystem.dto.flight.FlightDtoIn;
import com.example.bookingsystem.dto.flight.FlightDtoOut;
import com.example.bookingsystem.responses.flight.FlightPageResponse;
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

@RequiredArgsConstructor
@RequestMapping("api/flights")
@RestController
@Tag(name = "Flights", description = "API for managing Flights.")
public class FlightController {
    private final FlightService flightService;

    @Operation(
            summary = "Add new flight to database.",
            description = "Creates a new flight object based on given values. Seats are generated and randomly assigned as reserved."
    )
    @ApiResponse(responseCode = "200", description = "Flight has been added to the database successfully.")
    @PostMapping("/add")
    public ResponseEntity<FlightDtoOut> addFlight(@RequestBody FlightDtoIn flightDtoIn) {
        FlightDtoOut flight = flightService.addFlight(flightDtoIn);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieves a list of flights from database based on given arguments.",
            description = "Retrieves flights by departure airport, arrival airport, departure time" +
                    ". Returns all flights (10 per page) if no filters are applied. It uses specification and pagination" +
                    " so that if there should be large amount of objects, the website won't crash. Slice is used because it has" +
                    " isLast value, which will be used in frontend to see data on other pages. It makes it possible to see next and previous pages." +
                    " This function is called when plane is created."
    )
    @ApiResponse(responseCode = "200", description = "List of suitable flights has been retrieved successfully.")
    @GetMapping()
    public ResponseEntity<FlightPageResponse> getFlight(
            @RequestParam(value = "departureAirport", required = false) String departureAirport,
            @RequestParam(value = "arrivalAirport", required = false) String arrivalAirport,
            @RequestParam(value = "departureTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        FlightPageResponse flightList = flightService.getFlights(departureAirport, arrivalAirport, departureTime, page, size);
        return new ResponseEntity<>(flightList, HttpStatus.OK);
    }

    @Operation(
            summary = "Get flight from database based on flight id.",
            description = "Retrieves a flight from database based on its id. Used for testing"
    )
    @ApiResponse(responseCode = "200", description = "Flight has been retrieved from database successfully.")
    @GetMapping("{id}")
    public ResponseEntity<FlightDtoOut> getFlightById(@PathVariable("id") long id) {
        FlightDtoOut flight = flightService.getFlightById(id);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }
}
