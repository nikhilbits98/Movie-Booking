package org.nikhil.movie.booking.facade.impl;

import org.nikhil.movie.booking.entities.Booking;
import org.nikhil.movie.booking.entities.Screen;
import org.nikhil.movie.booking.entities.Seat;
import org.nikhil.movie.booking.entities.Show;
import org.nikhil.movie.booking.facade.BookingManager;
import org.nikhil.movie.booking.services.*;
import org.nikhil.movie.booking.services.impl.*;

import java.util.List;
import java.util.stream.Collectors;

public class BookingManagerImpl implements BookingManager {

    private static volatile BookingManager bookingManager;
    private final TheatreService theatreService;
    private final ScreenService screenService;
    private final SeatService seatService;
    private final MovieService movieService;
    private final ShowService showService;
    private final BookingService boookingService;

    private BookingManagerImpl() {
        theatreService = TheatreServiceImpl.getInstance();
        screenService = ScreenServiceImpl.getInstance();
        seatService = SeatServiceImpl.getInstance();
        movieService = MovieServiceImpl.getInstance();
        showService = ShowServiceImpl.getInstance();
        boookingService = BookingServiceImpl.getInstance();
    }

    public static BookingManager getInstance() {
        if(bookingManager == null) {
            synchronized (AdminManagerImpl.class) {
                if(bookingManager == null) {
                    bookingManager = new BookingManagerImpl();
                }
            }
        }
        return bookingManager;
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
