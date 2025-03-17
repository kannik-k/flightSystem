package com.example.bookingsystem.dto.flight;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@Schema(description = "Data transfer object for flight. Client sends to server.")
public class FlightDtoIn {
    @NotNull
    private String flightNumber;
    @NotNull
    private String departureAirport;
    @NotNull
    private String arrivalAirport;
    @NotNull
    private LocalDateTime departureTime;
    @NotNull
    private LocalDateTime arrivalTime;
    @NotNull
    private Double price;
}
