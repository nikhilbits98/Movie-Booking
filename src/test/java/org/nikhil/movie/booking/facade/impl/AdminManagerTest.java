package org.nikhil.movie.booking.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nikhil.movie.booking.entities.*;
import org.nikhil.movie.booking.facade.AdminManager;
import org.nikhil.movie.booking.services.*;
import org.nikhil.movie.booking.services.impl.*;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminManagerTest {

    private AdminManager adminManager;
    private TheatreService theatreService;
    private ScreenService screenService;
    private SeatService seatService;
    private MovieService movieService;
    private ShowService showService;

    @BeforeEach
    void setUp() {
        adminManager = AdminManagerImpl.getInstance();
        theatreService = TheatreServiceImpl.getInstance();
        screenService = ScreenServiceImpl.getInstance();
        seatService = SeatServiceImpl.getInstance();
        movieService = MovieServiceImpl.getInstance();
        showService = ShowServiceImpl.getInstance();
    }

    @Test
    void integrationTests() {
        System.out.println("Integration tests");
        String theatreName = "Theatre 1";
        Theatre theatre = adminManager.createTheatre(theatreName);
        assertEquals(theatreName, theatre.getName());
        List<Theatre> allTheatres = theatreService.getAllTheatres();
        assertEquals(1, allTheatres.size());
        assertEquals(theatre, allTheatres.get(0));
        String theatreId = theatre.getId();
        Theatre theatreById = theatreService.getTheatreById(theatre.getId());
        assertEquals(theatre, theatreById);
        String screenName = "Screen 1";
        Screen screen = adminManager.addScreenToTheatre(theatreId, screenName);
        assertEquals(screenName, screen.getName());
        List<Screen> screensByTheatreId = theatreService.getScreensByTheatreId(theatreId);
        assertEquals(1, screensByTheatreId.size());
        assertEquals(screen, screensByTheatreId.get(0));
        String screenId = screen.getId();
        Screen screenById = screenService.getScreenById(screenId);
        assertEquals(screen, screenById);
        Integer rowNumber = 1;
        Integer seatNumber = 1;
        Seat seat = adminManager.addSeatToScreen(screenId, rowNumber, seatNumber);
        assertEquals(rowNumber, seat.getRowNumber());
        assertEquals(seatNumber, seat.getSeatNumber());
        Seat seatById = seatService.getSeatById(seat.getId());
        assertEquals(seat, seatById);
        List<Seat> seatsByScreenId = screenService.getSeatsByScreenId(screenId);
        assertEquals(1, seatsByScreenId.size());
        assertEquals(seat, seatsByScreenId.get(0));
        String movieName = "Movie 1";
        Movie movie = adminManager.createMovie(movieName);
        assertEquals(movieName, movie.getName());
        String movieId = movie.getId();
        Movie movieById = movieService.getMovieById(movieId);
        assertEquals(movie, movieById);
        LocalTime startTime = LocalTime.of(10, 0);
        Integer durationInSeconds = 3600;
        Show show = adminManager.createShow(movieId, screenId, startTime, durationInSeconds);
        assertEquals(movie, show.getMovie());
        assertEquals(screen, show.getScreen());
        assertEquals(startTime, show.getStartTime());
        assertEquals(durationInSeconds, show.getDurationInSeconds());
        Show showById = showService.getShowById(show.getId());
        assertEquals(show, showById);
        System.out.println("Integration tests passed");
    }
}