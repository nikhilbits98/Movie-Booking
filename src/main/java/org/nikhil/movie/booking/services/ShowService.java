package org.nikhil.movie.booking.services;

import org.nikhil.movie.booking.entities.Movie;
import org.nikhil.movie.booking.entities.Screen;
import org.nikhil.movie.booking.entities.Show;

import java.time.LocalTime;
import java.util.List;

public interface ShowService {
    Show createShow(Screen screen, Movie movie, LocalTime showTime, Integer durationInSeconds);
    Show getShowById(String showId);
    List<Show> getAllShows();
}
