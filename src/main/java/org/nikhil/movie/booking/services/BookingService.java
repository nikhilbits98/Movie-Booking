package org.nikhil.movie.booking.services;

import org.nikhil.movie.booking.entities.Booking;
import org.nikhil.movie.booking.entities.Seat;
import org.nikhil.movie.booking.entities.Show;

import java.util.List;

public interface BookingService {
    Booking createBooking(Show show, List<Seat> seats, String bookedBy);

    List<Booking> getActiveBookingsByShowId(String showId);

    List<Seat> getBookedSeatsByShowId(String showId);

    Booking getBookingById(String bookingId);
}
