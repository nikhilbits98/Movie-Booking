package org.nikhil.movie.booking.entities;

import org.nikhil.movie.booking.enums.BookingStatus;

public class Booking {
    private final Show show;
    private final Seat seat;
    private BookingStatus bookingStatus;
    private String bookedBy;

    public Booking(Show show, Seat seat, String bookedBy) {
        this.show = show;
        this.seat = seat;
        this.bookingStatus = BookingStatus.CREATED;
        this.bookedBy = bookedBy;
    }


}
