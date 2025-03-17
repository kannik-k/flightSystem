package com.example.bookingsystem.controllers.flight;

import com.example.bookingsystem.dto.flight.FlightDtoIn;
import com.example.bookingsystem.dto.flight.FlightDtoOut;
import com.example.bookingsystem.services.flight.FlightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
@Tag(name = "Flights", description = "API for managing Flights.")
public class FlightController {
    private final FlightService flightService;

    @PostMapping("flight")
    public ResponseEntity<FlightDtoOut> addFlight(@RequestBody FlightDtoIn flightDtoIn) {
        FlightDtoOut flight = flightService.addFlight(flightDtoIn);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }
}
