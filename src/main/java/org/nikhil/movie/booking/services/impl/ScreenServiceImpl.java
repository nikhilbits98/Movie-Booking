package org.nikhil.movie.booking.services.impl;

import org.nikhil.movie.booking.entities.Screen;
import org.nikhil.movie.booking.entities.Seat;
import org.nikhil.movie.booking.services.ScreenService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScreenServiceImpl implements ScreenService {

    private static Map<String, Screen> screenIdToScreenMap;

    public ScreenServiceImpl() {
        screenIdToScreenMap = new HashMap<>();
    }

    @Override
    public Screen createScreen(String screenName, String theatreId) {
        Screen screen = new Screen(screenName);
        screenIdToScreenMap.put(screen.getId(), screen);
        return screen;
    }

    @Override
    public Screen getScreenById(String screenId) {
        return screenIdToScreenMap.get(screenId);
    }

    @Override
    public List<Seat> getSeatsByScreenId(String screenId) {
        return screenIdToScreenMap.get(screenId).getSeats();
    }
}
