package com.example.bookingsystem.controllers.seat;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
@Tag(name = "Seat", description = "API for managing seats.")
public class SeatController {

    //get seats

    // change random amount of seats to not available

    // change all seats to available

    // if plane is added generate all seats for it and every on is availabe
}
