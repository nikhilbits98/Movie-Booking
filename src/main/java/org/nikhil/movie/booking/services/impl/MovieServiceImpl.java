package org.nikhil.movie.booking.services.impl;

import org.nikhil.movie.booking.entities.Movie;
import org.nikhil.movie.booking.services.MovieService;

import java.util.HashMap;
import java.util.Map;

public class MovieServiceImpl implements MovieService {

    private static Map<String, Movie> movieIdToMovieMap;

    public MovieServiceImpl() {
        movieIdToMovieMap = new HashMap<>();
    }

    @Override
    public Movie createMovie(String name) {
        Movie movie = new Movie(name);
        movieIdToMovieMap.put(movie.getId(), movie);
        return movie;
    }

    @Override
    public Movie getMovieById(String id) {
        return movieIdToMovieMap.get(id);
    }
}
