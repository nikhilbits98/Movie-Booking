package org.nikhil.movie.booking.facade;

import org.nikhil.movie.booking.entities.*;

import java.time.LocalTime;

public interface AdminManager {
    Theatre createTheatre(String theatreName);
    Screen addScreenToTheatre(String theatreId, String screenName);
    Seat addSeatToScreen(String screenId, Integer rowNumber, Integer seatNumber);
    Movie createMovie(String movieName);
    Show createShow(String movieId, String screenId, LocalTime startTime, Integer durationInSeconds);
}
