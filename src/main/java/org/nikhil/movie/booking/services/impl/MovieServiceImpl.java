package org.nikhil.movie.booking.services.impl;

import org.nikhil.movie.booking.services.MovieService;
import org.nikhil.movie.booking.entities.Movie;

import java.util.HashMap;
import java.util.Map;

public class MovieServiceImpl implements MovieService {

    private static volatile MovieService movieService;
    private static Map<String,Movie> movieIdToMovieMap;

    private MovieServiceImpl(){
        movieIdToMovieMap = new HashMap<>();
    }

    public static MovieService getInstance(){
        if(movieService == null){
            synchronized (MovieServiceImpl.class){
                if(movieService == null){
                    movieService = new MovieServiceImpl();
                }
            }
        }
        return movieService;
    }

    @Override
    public Movie createMovie(String name) {
        Movie movie = new Movie(name);
        movieIdToMovieMap.put(movie.getId(),movie);
        return movie;
    }

    @Override
    public Movie getMovieById(String id) {
        return movieIdToMovieMap.get(id);
    }
}
