package com.example.bookingsystem.dto.seat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Data transfer object for seat. Server sends to client.")
public class SeatDtoOut {
    @Schema(description = "Unique seat id", example = "1")
    private long seatId;
    @Schema(description = "Unique flight id", example = "3")
    private long flightId;
    @Schema(description = "Seat number", example = "5C")
    private String seatNumber;
    @Schema(description = "Seat class type", example = "first class")
    private String classType;
    @Schema(description = "Indicates if seat is near exit", example = "false")
    private Boolean isNearExit;
    @Schema(description = "Shows if seat has more leg room", example = "false")
    private Boolean hasExtraLegroom;
    @Schema(description = "Shows if seat is reserved", example = "false")
    private Boolean isReserved;
}
