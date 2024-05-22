package org.nikhil.movie.booking.facade.impl;

import org.nikhil.movie.booking.entities.Booking;
import org.nikhil.movie.booking.entities.Screen;
import org.nikhil.movie.booking.entities.Seat;
import org.nikhil.movie.booking.entities.Show;
import org.nikhil.movie.booking.facade.BookingManager;
import org.nikhil.movie.booking.services.BookingService;
import org.nikhil.movie.booking.services.SeatService;
import org.nikhil.movie.booking.services.ShowService;
import org.nikhil.movie.booking.services.impl.BookingServiceImpl;
import org.nikhil.movie.booking.services.impl.SeatServiceImpl;
import org.nikhil.movie.booking.services.impl.ShowServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class BookingManagerImpl implements BookingManager {

    private final SeatService seatService;
    private final ShowService showService;
    private final BookingService boookingService;

    public BookingManagerImpl() {
        seatService = new SeatServiceImpl();
        showService = new ShowServiceImpl();
        boookingService = new BookingServiceImpl();
    }

    @Override
    public List<Seat> getAvailableSeats(String showId) {
        Show show = showService.getShowById(showId);
        Screen screen = show.getScreen();
        List<Seat> bookedSeats = boookingService.getBookedSeatsByShowId(showId);
        return screen.getSeats().stream().filter(seat -> !bookedSeats.contains(seat)).collect(Collectors.toList());
    }

    @Override
    public Booking createBooking(String showId, List<String> seatIds, String bookedBy) {
        Show show = showService.getShowById(showId);
        List<Seat> seats = seatIds.stream().map(seatService::getSeatById).collect(Collectors.toList());
        return boookingService.createBooking(show, seats, bookedBy);
    }

    @Override
    public void markPaymentSuccess(String bookingId) {
        Booking booking = boookingService.getBookingById(bookingId);
        booking.markBooked();
    }

    @Override
    public void markPaymentFailed(String bookingId) {
        Booking booking = boookingService.getBookingById(bookingId);
        booking.markFailed();
    }
}
