package com.example.bookingsystem.entities.flight;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name="flight")
public class FlightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flight_id;
    @Column
    private String flight_number;
    @Column
    private String departure_airport;
    @Column
    private String arrival_airport;
    @Column
    private LocalDateTime departure_time;
    @Column
    private LocalDateTime arrival_time;
    @Column
    private Double price;
}
