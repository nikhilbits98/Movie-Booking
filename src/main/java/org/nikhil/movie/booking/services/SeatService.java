package org.nikhil.movie.booking.services;

import org.nikhil.movie.booking.entities.Seat;

public interface SeatService {
    Seat createSeat(Integer rowNumber, Integer seatNumber);

    Seat getSeatById(String seatId);
}
