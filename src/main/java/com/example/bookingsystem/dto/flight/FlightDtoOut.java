package com.example.bookingsystem.dto.flight;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Data transfer object for flight. Server sends to client.")
public class FlightDtoOut {
    @Schema(description = "unique flight id", example = "1")
    private long flightId;
    @Schema(description = "flight number", example = "BT851")
    private String flightNumber;
    @Schema(description = "departure airport", example = "Tallinn")
    private String departureAirport;
    @Schema(description = "arrival airport", example = "Riga")
    private String arrivalAirport;
    @Schema(description = "departure time", example = "2025-04-01T09:00:00")
    private LocalDateTime departureTime;
    @Schema(description = "arrival time", example = "2025-04-01T10:00:00")
    private LocalDateTime arrivalTime;
    @Schema(description = "price per seat", example = "20.0")
    private Double price;
}
