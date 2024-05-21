package org.nikhil.movie.booking.entities;

import lombok.Getter;
import org.nikhil.movie.booking.enums.BookingStatus;

import java.util.List;
import java.util.UUID;

@Getter
public class Booking {
    private final String id;
    private final Show show;
    private final List<Seat> seats;
    private BookingStatus bookingStatus;
    private String bookedBy;

    public Booking(Show show, List<Seat> seats, String bookedBy) {
        this.id = UUID.randomUUID().toString();
        this.show = show;
        this.seats = seats;
        this.bookingStatus = BookingStatus.CREATED;
        this.bookedBy = bookedBy;
    }

    public void markBooked() {
        this.bookingStatus = BookingStatus.BOOKED;
    }

    public void markFailed() {
        this.bookingStatus = BookingStatus.FAILED;
    }
}
