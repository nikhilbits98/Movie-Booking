package org.nikhil.movie.booking.services.impl;

import org.nikhil.movie.booking.entities.Movie;
import org.nikhil.movie.booking.entities.Screen;
import org.nikhil.movie.booking.entities.Show;
import org.nikhil.movie.booking.services.ShowService;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ShowServiceImpl implements ShowService {

    private static volatile ShowService showService;
    private static Map<String,Show> showIdToShowMap;

    private ShowServiceImpl(){
        showIdToShowMap = new HashMap<>();
    }

    public static ShowService getInstance(){
        if(showService == null){
            synchronized (ShowServiceImpl.class){
                if(showService == null){
                    showService = new ShowServiceImpl();
                }
            }
        }
        return showService;
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
}
