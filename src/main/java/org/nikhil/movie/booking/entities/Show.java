package org.nikhil.movie.booking.entities;

import lombok.Getter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
public class Show {
    private final String id;
    private final Screen screen;
    private final Movie movie;
    private final LocalTime startTime;
    private final Integer durationInSeconds;

    public Show(Screen screen, Movie movie, LocalTime startTime, Integer durationInSeconds) {
        this.id = UUID.randomUUID().toString();
        this.screen = screen;
        this.movie = movie;
        this.startTime = startTime;
        this.durationInSeconds = durationInSeconds;
    }
}
