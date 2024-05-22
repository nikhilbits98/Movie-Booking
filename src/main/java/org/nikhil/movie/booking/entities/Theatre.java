package org.nikhil.movie.booking.entities;

import lombok.Getter;

import java.util.*;

@Getter
public class Theatre {
    private final String id;
    private final String name;
    private final Map<String, Screen> screenIdToScreenMap;

    public Theatre(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.screenIdToScreenMap = new HashMap<>();
    }

    public void addScreen(Screen screen) {
        screenIdToScreenMap.put(screen.getId(), screen);
    }

    public List<Screen> getScreens() {
        return new ArrayList<>(screenIdToScreenMap.values());
    }
}
