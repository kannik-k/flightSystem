package com.example.bookingsystem.services.seat;

import com.example.bookingsystem.dto.seat.SeatDtoOut;
import com.example.bookingsystem.entities.seat.SeatEntity;
import com.example.bookingsystem.mappers.seat.SeatMapper;
import com.example.bookingsystem.repositories.seat.SeatRepository;
import com.example.bookingsystem.specifications.seat.SeatSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final List<String> seatLetters = Arrays.asList("A", "B", "C", "D", "E", "F");

    /**
     * Generate seat.
     *
     * Generates seats when plane is added to the database. Letters are defined in seatLetters list. Seat will be given
     * values according to the rules as described in README file (https://github.com/kannik-k/flightSystem).
     **/
    public void generateSeats(Long flightId) {
        List<SeatEntity> seats = new ArrayList<>();
        for (int row = 1; row <= 33; row++) {
            for (String letter : seatLetters) {
                SeatEntity seatEntity = new SeatEntity();
                seatEntity.setSeatNumber(row + letter);
                seatEntity.setFlightId(flightId);

                // Should be replaced by enum
                if (row <= 4) {
                    seatEntity.setClassType("first");
                } else if (row <= 7) {
                    seatEntity.setClassType("business");
                } else {
                    seatEntity.setClassType("economy");
                }

                seatEntity.setHasExtraLegroom(row == 14 || row <= 4 || (row >= 5 && row <= 7));

                seatEntity.setIsNearExit(row <= 5 || row >= 29);

                seatEntity.setIsReserved(false);
                seats.add(seatEntity);
            }
        }
        seatRepository.saveAll(seats);
    }

    public List<SeatDtoOut> randomSeatsBooked(Long flightId) {
        List<SeatEntity> seats = seatRepository.findAllByFlightId(flightId);

        if (seats.isEmpty()) {
            throw new IllegalStateException("No seats found for flight ID: " + flightId);
        }

        Random random = new Random();
        double randomPercentage = 0.1 + (0.5 * random.nextDouble());
        int seatsToBook = (int) (seats.size() * randomPercentage);
        Collections.shuffle(seats);

        for (int i = 0; i < seatsToBook; i++) {
            seats.get(i).setIsReserved(true);
        }

        for (int i = seatsToBook; i < seats.size(); i++) {
            seats.get(i).setIsReserved(false);
        }

        seatRepository.saveAll(seats);

        return seatMapper.toDtoList(seats.stream().filter(SeatEntity::getIsReserved).toList());
    }

    /**
     * Finds and groups available seats based on the user's requirements.
     * If the seats are required to be together (`seatsTogether`), the function will attempt to group them based on specific seat patterns.
     *
     * The grouping logic is as follows:
     * - If `seatsTogether` is true, the function will first try to find a row where window seats (A and F) are available.
     *   A window seat is considered to be any seat that ends with 'A' or 'F'.
     * - If 3 or more seats are required:
     *   - For 3 seats, it looks for a consecutive group of 3 seats (e.g., 1A 1B 1C).
     *   - For 4 seats, it looks for two pairs (e.g., 1A 1B and 1C 1D).
     *   - For 5 seats, it looks for a combination of a group of 3 and a pair (e.g., 1A 1B 1C and 1D 1E).
     *   - For 6 seats, it looks for two groups of 3 consecutive seats (e.g., 1A 1B 1C and 1D 1E 1F).
     * - If `seatsTogether` is false, the function will ignore the window seat logic and simply try to find any available seats that are consecutive, without the requirement for them to be window seats.
     */
    public List<SeatDtoOut> getAllByFlightId(Long flightId, String classType, Boolean isNearExit,
                                             Boolean hasExtraLegroom, Integer seatNums, Boolean windowSeat, Boolean seatsTogether) throws IllegalArgumentException {
        if (seatNums > 198) {
            throw new IllegalArgumentException("Number of seats exceeds seats on plane " + flightId);
        }

        Specification<SeatEntity> spec = Specification.where(SeatSpecification.getByFlightId(flightId)
                .and(SeatSpecification.getByClassType(classType))
                .and(SeatSpecification.getByIsNearExit(isNearExit))
                .and(SeatSpecification.getByHasExtraLegroom(hasExtraLegroom))
                .and(SeatSpecification.getByIsReserved(false)));

        List<SeatEntity> seats = seatRepository.findAll(spec);

        if (seatsTogether) {
            seats = groupSeatsTogether(seats, seatNums);
        }

        if (windowSeat && !seatsTogether) {
            seats = seats.stream()
                    .filter(seat -> seat.getSeatNumber().endsWith("A") || seat.getSeatNumber().endsWith("F"))
                    .toList();
        }

        return seats.stream().limit(seatNums).map(seatMapper::toDto).toList();
    }

    /**
     * Get all seats.
     *
     * Returns all seats of the flight. Takes in plane id, each seat has plane id (acts as foreign key) and returns seats
     * that belong to this specific flight.
     */
    public List<SeatDtoOut> getByFlightId(Long flightId) throws ChangeSetPersister.NotFoundException {
        List<SeatEntity> seats = seatRepository.findAllByFlightId(flightId);
        if (seats.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return seatMapper.toDtoList(seats);
    }

    /**
     * Return free seats.
     *
     * Returns seats that are not reserved. For testing purposes.
     */
    public List<SeatDtoOut> getFreeSeats(Long id) {
        return seatRepository.findAllByFlightId(id).stream()
                .filter(seat -> !seat.getIsReserved())
                .map(seatMapper::toDto)
                .toList();
    }

    private List<SeatEntity> groupSeatsTogether(List<SeatEntity> availableSeats, int seatNums) {
        List<SeatEntity> groupedSeats = new ArrayList<>();
        int neededSeats = seatNums;

        if (neededSeats == 2) {
            for (int i = 0; i < availableSeats.size() - 1; i++) {
                SeatEntity seat1 = availableSeats.get(i);
                SeatEntity seat2 = availableSeats.get(i + 1);
                if (areSeatsTogether(seat1, seat2)) {
                    groupedSeats.add(seat1);
                    groupedSeats.add(seat2);
                    break;
                }
            }
        }

        else if (neededSeats == 3) {
            for (int i = 0; i < availableSeats.size() - 2; i++) {
                SeatEntity seat1 = availableSeats.get(i);
                SeatEntity seat2 = availableSeats.get(i + 1);
                SeatEntity seat3 = availableSeats.get(i + 2);
                if (areSeatsTogether(seat1, seat2, seat3)) {
                    groupedSeats.add(seat1);
                    groupedSeats.add(seat2);
                    groupedSeats.add(seat3);
                    break;
                }
            }
        }

        else if (neededSeats == 4) {
            for (int i = 0; i < availableSeats.size() - 3; i++) {
                SeatEntity seat1 = availableSeats.get(i);
                SeatEntity seat2 = availableSeats.get(i + 1);
                SeatEntity seat3 = availableSeats.get(i + 2);
                SeatEntity seat4 = availableSeats.get(i + 3);
                if (areSeatsTogether(seat1, seat2) && areSeatsTogether(seat3, seat4)) {
                    groupedSeats.add(seat1);
                    groupedSeats.add(seat2);
                    groupedSeats.add(seat3);
                    groupedSeats.add(seat4);
                    break;
                }
            }
        }

        else if (neededSeats == 5) {
            for (int i = 0; i < availableSeats.size() - 4; i++) {
                SeatEntity seat1 = availableSeats.get(i);
                SeatEntity seat2 = availableSeats.get(i + 1);
                SeatEntity seat3 = availableSeats.get(i + 2);
                SeatEntity seat4 = availableSeats.get(i + 3);
                SeatEntity seat5 = availableSeats.get(i + 4);
                if (areSeatsTogether(seat1, seat2, seat3) && areSeatsTogether(seat4, seat5)) {
                    groupedSeats.add(seat1);
                    groupedSeats.add(seat2);
                    groupedSeats.add(seat3);
                    groupedSeats.add(seat4);
                    groupedSeats.add(seat5);
                    break;
                }
            }
        }

        else if (neededSeats == 6) {
            for (int i = 0; i < availableSeats.size() - 5; i++) {
                SeatEntity seat1 = availableSeats.get(i);
                SeatEntity seat2 = availableSeats.get(i + 1);
                SeatEntity seat3 = availableSeats.get(i + 2);
                SeatEntity seat4 = availableSeats.get(i + 3);
                SeatEntity seat5 = availableSeats.get(i + 4);
                SeatEntity seat6 = availableSeats.get(i + 5);
                if (areSeatsTogether(seat1, seat2, seat3) && areSeatsTogether(seat4, seat5, seat6)) {
                    groupedSeats.add(seat1);
                    groupedSeats.add(seat2);
                    groupedSeats.add(seat3);
                    groupedSeats.add(seat4);
                    groupedSeats.add(seat5);
                    groupedSeats.add(seat6);
                    break;
                }
            }
        }

        return groupedSeats;
    }

    private boolean areSeatsTogether(SeatEntity seat1, SeatEntity seat2) {
        String seatNumber1 = seat1.getSeatNumber();
        String seatNumber2 = seat2.getSeatNumber();
        int row1 = Integer.parseInt(seatNumber1.substring(0, seatNumber1.length() - 1));
        int row2 = Integer.parseInt(seatNumber2.substring(0, seatNumber2.length() - 1));

        return row1 == row2 && Math.abs(seatLetters.indexOf(seatNumber1.substring(seatNumber1.length() - 1)) - seatLetters.indexOf(seatNumber2.substring(seatNumber2.length() - 1))) == 1;
    }

    private boolean areSeatsTogether(SeatEntity seat1, SeatEntity seat2, SeatEntity seat3) {
        return areSeatsTogether(seat1, seat2) && areSeatsTogether(seat2, seat3);
    }

}
