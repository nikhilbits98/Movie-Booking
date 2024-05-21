package org.nikhil.movie.booking.services.impl;

import org.nikhil.movie.booking.entities.Seat;
import org.nikhil.movie.booking.services.SeatService;

import java.util.HashMap;
import java.util.Map;

public class SeatServiceImpl implements SeatService {

    private static volatile SeatService seatService;
    private static Map<String, Seat> seatIdToSeatMap;

    private SeatServiceImpl(){
        seatIdToSeatMap = new HashMap<>();
    }

    public static SeatService getInstance(){
        if(seatService == null){
            synchronized (SeatServiceImpl.class){
                if(seatService == null){
                    seatService = new SeatServiceImpl();
                }
            }
        }
        return seatService;
    }

    @Override
    public Seat createSeat(Integer rowNumber, Integer seatNumber) {
        Seat seat = new Seat(rowNumber, seatNumber);
        seatIdToSeatMap.put(seat.getId(),seat);
        return seat;
    }

    @Override
    public Seat getSeatById(String seatId) {
        return seatIdToSeatMap.get(seatId);
    }
}
