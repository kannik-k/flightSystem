package com.example.bookingsystem.controllers.seat;

import com.example.bookingsystem.dto.seat.SeatDtoOut;
import com.example.bookingsystem.services.seat.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
@Tag(name = "Seat", description = "API for managing seats.")
public class SeatController {

    private final SeatService seatService;

    @Operation(
            summary = "Get flights seats",
            description = "Get plane seats by plane id"
    )
    @ApiResponse(responseCode = "200", description = "Planes seats have been retrieved from database successfully.")
    @GetMapping("seats/{id}")
    public ResponseEntity<List<SeatDtoOut>> getSeatByFlightId(@PathVariable Long id) throws Exception {
        List<SeatDtoOut> seats = seatService.getByFlightId(id);
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }
}
