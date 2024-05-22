package org.nikhil.movie.booking.services;

import org.nikhil.movie.booking.entities.Movie;

public interface MovieService {
    Movie createMovie(String name);

    Movie getMovieById(String id);
}
