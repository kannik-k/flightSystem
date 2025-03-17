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
    @Schema(description = "flight number", example = "FR5694")
    private String flightNumber;
    @NotNull
    @Schema(description = "departure airport", example = "Tallinn")
    private String departureAirport;
    @NotNull
    @Schema(description = "arrival airport", example = "Dublin")
    private String arrivalAirport;
    @NotNull
    @Schema(description = "departure time", example = "2025-04-01T08:00:00")
    private LocalDateTime departureTime;
    @NotNull
    @Schema(description = "arrival time", example = "2025-04-01T09:00:00")
    private LocalDateTime arrivalTime;
    @NotNull
    @Schema(description = "price of the flight (per seat)", example = "35.0")
    private Double price;
}
