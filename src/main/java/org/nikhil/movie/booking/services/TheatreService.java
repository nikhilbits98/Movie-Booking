package org.nikhil.movie.booking.services;

import org.nikhil.movie.booking.entities.Screen;
import org.nikhil.movie.booking.entities.Theatre;

import java.util.List;

public interface TheatreService {
    Theatre createTheatre(String theatreName);

    List<Theatre> getAllTheatres();

    Theatre getTheatreById(String theatreId);

    List<Screen> getScreensByTheatreId(String theatreId);
}
