package org.nikhil.movie.booking.facade.impl;

import org.nikhil.movie.booking.entities.*;
import org.nikhil.movie.booking.facade.AdminManager;
import org.nikhil.movie.booking.services.*;
import org.nikhil.movie.booking.services.impl.*;

import java.time.LocalTime;

public class AdminManagerImpl implements AdminManager {

    private final TheatreService theatreService;
    private final ScreenService screenService;
    private final SeatService seatService;
    private final MovieService movieService;
    private final ShowService showService;

    public AdminManagerImpl() {
        theatreService = new TheatreServiceImpl();
        screenService = new ScreenServiceImpl();
        seatService = new SeatServiceImpl();
        movieService = new MovieServiceImpl();
        showService = new ShowServiceImpl();
    }

    @Override
    public Theatre createTheatre(String theatreName) {
        return theatreService.createTheatre(theatreName);
    }

    @Override
    public Screen addScreenToTheatre(String theatreId, String screenName) {
        Screen screen = screenService.createScreen(screenName, theatreId);
        Theatre theatre = theatreService.getTheatreById(theatreId);
        theatre.addScreen(screen);
        return screen;
    }

    @Override
    public Seat addSeatToScreen(String screenId, Integer rowNumber, Integer seatNumber) {
        Screen screen = screenService.getScreenById(screenId);
        Seat seat = seatService.createSeat(rowNumber, seatNumber);
        screen.addSeat(seat);
        return seat;
    }

    @Override
    public Movie createMovie(String movieName) {
        return movieService.createMovie(movieName);
    }

    @Override
    public Show createShow(String movieId, String screenId, LocalTime startTime, Integer durationInSeconds) {
        Movie movie = movieService.getMovieById(movieId);
        Screen screen = screenService.getScreenById(screenId);
        return showService.createShow(screen,movie, startTime, durationInSeconds);
    }
}
