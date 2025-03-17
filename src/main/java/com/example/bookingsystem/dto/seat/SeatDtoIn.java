package com.example.bookingsystem.dto.seat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Data transfer object for flight. Client sends to server")
public class SeatDtoIn {
    @NotNull
    @Schema(description = "Unique flight id", example = "1")
    private long flightId;
    @NotNull
    @Schema(description = "Unique seat number", example = "1A")
    private String seatNumber;
    @NotNull
    @Schema(description = "Seat class type", example = "first class")
    private String classType;
    @NotNull
    @Schema(description = "Indicates if seat is near exit", example = "true")
    private Boolean isNearExit;
    @NotNull
    @Schema(description = "Shows if seat has more leg room", example = "true")
    private Boolean hasExtraLegroom;
    @NotNull
    @Schema(description = "Shows if seat is reserved", example = "true")
    private Boolean isReserved;
}
