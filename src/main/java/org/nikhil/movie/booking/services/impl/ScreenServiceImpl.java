package org.nikhil.movie.booking.services.impl;

import org.nikhil.movie.booking.entities.Screen;
import org.nikhil.movie.booking.entities.Seat;
import org.nikhil.movie.booking.services.ScreenService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScreenServiceImpl implements ScreenService {

    private static volatile ScreenService screenService;
    private static Map<String, Screen> screenIdToScreenMap;

    private ScreenServiceImpl(){
        screenIdToScreenMap = new HashMap<>();
    }

    public static ScreenService getInstance() {
        if(screenService == null) {
            synchronized (TheatreServiceImpl.class) {
                if(screenService == null) {
                    screenService = new ScreenServiceImpl();
                }
            }
        }
        return screenService;
    }

    @Override
    public Screen createScreen(String screenName, String theatreId) {
        Screen screen = new Screen(screenName);
        screenIdToScreenMap.put(screen.getId(),screen);
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
