package com.example.bookingsystem.entities.seat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "seat")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    @Column
    private Long flightId;
    @Column
    private String seatNumber;
    @Column
    private String classType;
    @Column
    private Boolean isNearExit;
    @Column
    private Boolean hasExtraLegroom;
    @Column
    private Boolean isReserved;
}
