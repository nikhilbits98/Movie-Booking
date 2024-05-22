package org.nikhil.movie.booking.services.impl;

import org.nikhil.movie.booking.entities.Screen;
import org.nikhil.movie.booking.entities.Theatre;
import org.nikhil.movie.booking.services.TheatreService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheatreServiceImpl implements TheatreService {

    private static Map<String, Theatre> theatreIdToTheatreMap;

    public TheatreServiceImpl() {
        theatreIdToTheatreMap = new HashMap<>();
    }

    @Override
    public Theatre createTheatre(String theatreName) {
        Theatre theatre = new Theatre(theatreName);
        theatreIdToTheatreMap.put(theatre.getId(), theatre);
        return theatre;
    }

    @Override
    public List<Theatre> getAllTheatres() {
        return new ArrayList<>(theatreIdToTheatreMap.values());
    }

    @Override
    public Theatre getTheatreById(String theatreId) {
        return theatreIdToTheatreMap.get(theatreId);
    }

    @Override
    public List<Screen> getScreensByTheatreId(String theatreId) {
        return theatreIdToTheatreMap.get(theatreId).getScreens();
    }
}
