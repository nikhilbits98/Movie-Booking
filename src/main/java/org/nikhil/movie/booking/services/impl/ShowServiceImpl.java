package org.nikhil.movie.booking.services.impl;

import org.nikhil.movie.booking.entities.Movie;
import org.nikhil.movie.booking.entities.Screen;
import org.nikhil.movie.booking.entities.Show;
import org.nikhil.movie.booking.services.ShowService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowServiceImpl implements ShowService {

    private static Map<String,Show> showIdToShowMap;

    public ShowServiceImpl(){
        showIdToShowMap = new HashMap<>();
    }

    @Override
    public Show createShow(Screen screen, Movie movie, LocalTime showTime, Integer durationInSeconds) {
        Show show = new Show(screen, movie, showTime, durationInSeconds);
        showIdToShowMap.put(show.getId(),show);
        return show;
    }

    @Override
    public Show getShowById(String showId) {
        return showIdToShowMap.get(showId);
    }

    @Override
    public List<Show> getAllShows() {
        return new ArrayList<>(showIdToShowMap.values());
    }
}
