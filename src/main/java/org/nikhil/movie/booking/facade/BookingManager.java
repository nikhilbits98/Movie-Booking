package org.nikhil.movie.booking.facade;

import org.nikhil.movie.booking.entities.Booking;
import org.nikhil.movie.booking.entities.Seat;

import java.util.List;

public interface BookingManager {
    List<Seat> getAvailableSeats(String showId);
    Booking createBooking(String showId, List<String> seatIds, String bookedBy);
    void markPaymentSuccess(String bookingId);
    void markPaymentFailed(String bookingId);
}
