package org.nikhil.movie.booking.services.impl;

import org.nikhil.movie.booking.entities.Seat;
import org.nikhil.movie.booking.services.SeatService;

import java.util.HashMap;
import java.util.Map;

public class SeatServiceImpl implements SeatService {

    private static Map<String, Seat> seatIdToSeatMap;

    public SeatServiceImpl() {
        seatIdToSeatMap = new HashMap<>();
    }

    @Override
    public Seat createSeat(Integer rowNumber, Integer seatNumber) {
        Seat seat = new Seat(rowNumber, seatNumber);
        seatIdToSeatMap.put(seat.getId(), seat);
        return seat;
    }

    @Override
    public Seat getSeatById(String seatId) {
        return seatIdToSeatMap.get(seatId);
    }
}
