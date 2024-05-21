package org.nikhil.movie.booking.facade.impl;

import org.nikhil.movie.booking.entities.*;
import org.nikhil.movie.booking.facade.AdminManager;
import org.nikhil.movie.booking.services.*;
import org.nikhil.movie.booking.services.impl.*;

import java.time.LocalTime;

public class AdminManagerImpl implements AdminManager {

    private static volatile AdminManager adminManager;
    private final TheatreService theatreService;
    private final ScreenService screenService;
    private final SeatService seatService;
    private final MovieService movieService;
    private final ShowService showService;

    private AdminManagerImpl() {
        theatreService = TheatreServiceImpl.getInstance();
        screenService = ScreenServiceImpl.getInstance();
        seatService = SeatServiceImpl.getInstance();
        movieService = MovieServiceImpl.getInstance();
        showService = ShowServiceImpl.getInstance();
    }

    public static AdminManager getInstance() {
        if(adminManager == null) {
            synchronized (AdminManagerImpl.class) {
                if(adminManager == null) {
                    adminManager = new AdminManagerImpl();
                }
            }
        }
        return adminManager;
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
