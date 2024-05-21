package org.nikhil.movie.booking.services;

import org.nikhil.movie.booking.entities.Screen;
import org.nikhil.movie.booking.entities.Seat;

import java.util.List;

public interface ScreenService {
    Screen createScreen(String screenName, String theatreId);
    Screen getScreenById(String screenId);
    List<Seat> getSeatsByScreenId(String screenId);
}
