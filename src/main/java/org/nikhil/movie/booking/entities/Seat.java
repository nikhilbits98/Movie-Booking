package org.nikhil.movie.booking.entities;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Seat {
    private final String id;
    private final Integer rowNumber;
    private final Integer seatNumber;

    public Seat(Integer rowNumber, Integer seatNumber) {
        this.id = UUID.randomUUID().toString();
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }
}
