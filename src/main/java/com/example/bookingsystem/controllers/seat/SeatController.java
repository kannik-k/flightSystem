package com.example.bookingsystem.controllers.seat;

import com.example.bookingsystem.dto.seat.SeatDtoOut;
import com.example.bookingsystem.services.seat.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/seats")
@RestController
@Tag(name = "Seat", description = "API for managing seats.")
public class SeatController {

    private final SeatService seatService;

    @Operation(
            summary = "Get flights seats",
            description = "Get suitable plane seats by plane id and given parameters. It uses specification " +
                    "so that if there should be large amount of objects, the website won't crash."
    )
    @ApiResponse(responseCode = "200", description = "Planes seats have been retrieved from database successfully.")
    @GetMapping("select/{id}")
    public ResponseEntity<List<SeatDtoOut>> getSeatByFlightId(
            @PathVariable Long id,
            @RequestParam(value = "classType", required = false) String classType,
            @RequestParam(value = "isNearExit", required = false) Boolean isNearExit,
            @RequestParam(value = "hasExtraLegroom", required = false) Boolean hasExtraLegroom,
            @RequestParam(defaultValue = "1", required = false) int seatNums,
            @RequestParam(defaultValue = "false", required = false) Boolean windowSeat,
            @RequestParam(defaultValue = "false", required = false) Boolean seatsTogether) throws IllegalArgumentException {
        System.out.println("Fetching seats for flight ID: " + id);
        System.out.println("ClassType: " + classType);
        System.out.println("IsNearExit: " + isNearExit);
        System.out.println("HasExtraLegroom: " + hasExtraLegroom);
        System.out.println("SeatNums: " + seatNums);
        System.out.println("WindowSeat: " + windowSeat);
        System.out.println("SeatsTogether: " + seatsTogether);
        List<SeatDtoOut> seats = seatService.getAllByFlightId(id, classType, isNearExit, hasExtraLegroom, seatNums, windowSeat, seatsTogether);
        System.out.println(seats);
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @Operation(
            summary = "Get flights seats",
            description = "Get all plane seats by plane id. USed in front end to get all seats."
    )
    @ApiResponse(responseCode = "200", description = "Plane seats have been retrieved from database successfully.")
    @GetMapping("{id}")
    public ResponseEntity<List<SeatDtoOut>> getAllFlightSeat(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        List<SeatDtoOut> seats = seatService.getByFlightId(id);
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @Operation(
            summary = "Get reserved seats",
            description = "Get all randomly generated reserved seats by plane id. Function is called in plane controller after seats are generated. Seats" +
                    " are randomly marked as reserved."
    )
    @ApiResponse(responseCode = "200", description = "Planes seats that are randomly generated have been retrieved from database successfully.")
    @GetMapping("reserved/{id}")
    public ResponseEntity<List<SeatDtoOut>> getReservedSeats(@PathVariable Long id) {
        List<SeatDtoOut> seats = seatService.randomSeatsBooked(id);
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all available seats",
            description = "Get all available seats on plane using plane id. This is mainly for testing purposes and is currently not used in frontend."
    )
    @ApiResponse(responseCode = "200", description = "Plane seats that are available have been retrieved from database successfully.")
    @GetMapping("available/{id}")
    public ResponseEntity<List<SeatDtoOut>> getFreeSeats(@PathVariable Long id) {
        List<SeatDtoOut> seats = seatService.getFreeSeats(id);
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }
}
