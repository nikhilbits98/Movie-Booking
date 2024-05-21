package org.nikhil.movie.booking.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Screen {
    private final String id;
    private final String name;
    private final List<Seat> allSeats;

    public Screen(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.allSeats = new ArrayList<>();
    }

    public void addSeat(Seat seat) {
        allSeats.add(seat);
    }

    public List<Seat> getSeats() {
        return allSeats;
    }
}
